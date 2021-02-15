package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentTimetableBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import abdulmanov.eduard.timetable.presentation._common.provides.LawProvider
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.timetable.presentation._common.extensions.getDaysOfWeekFromLocale
import abdulmanov.eduard.timetable.presentation._common.extensions.getScreenSize
import abdulmanov.eduard.timetable.presentation.timetable.adapters.MultipleClassesDelegateAdapter
import abdulmanov.eduard.timetable.presentation.timetable.helpers.caledar.TimetableDayBinder
import abdulmanov.eduard.timetable.presentation.timetable.helpers.caledar.TimetableMonthHeaderBinder
import abdulmanov.eduard.timetable.presentation.timetable.helpers.speed_dial.SpeedDialDelegate
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import abdulmanov.eduard.timetable.presentation.timetable.adapters.OneTimeClassesDelegateAdapter
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.yearMonth
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TimetableFragment: BaseFragment<FragmentTimetableBinding>(),
    MultipleClassesDelegateAdapter.ClickListener,
    OneTimeClassesDelegateAdapter.ClickListener {

    @Inject
    lateinit var lawProvider: LawProvider

    private val viewModel by initViewModel<TimetableViewModel>()

    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    private var isCollapse = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(::onBackPressed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.classes.observe(viewLifecycleOwner, { (binding.recyclerView.adapter as CompositeDelegateAdapter).swapData(it)})
    }

    private fun onBackPressed(){
        if(binding.speedDialView.isOpen){
            binding.speedDialView.close()
        }else{
            viewModel.onBackCommandClick()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.openSettingItem -> viewModel.openScreenSetting()
        }
        return true
    }

    override fun onMultipleClassClick(multipleClass: MultipleClassPresentationModel) {
        viewModel.openScreenMultipleClass(multipleClass)
    }

    override fun onOneTimeClassClick(oneTimeClass: OneTimeClassPresentationModel) {
        viewModel.openScreenOneTimeClass(oneTimeClass)
    }

    private fun initUI(){
        binding.timetableToolbar.run {
            setTitle(R.string.timetable_toolbar_title)
            inflateMenu(R.menu.menu_timetable)
            setOnMenuItemClickListener(this@TimetableFragment::onOptionsItemSelected)
        }

        binding.dateTextView.run {
            text = selectionFormatter.format(LocalDate.now())
            setOnClickListener {
                collapseOrExpandCalendar()
            }
        }

        binding.calendarView.run {
            val screenSize = context.getScreenSize()
            daySize = Size(screenSize.x/7,screenSize.x/10)

            val daysOfWeek = getDaysOfWeekFromLocale()
            val currentMonth = YearMonth.now()

            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToDate(LocalDate.now())
            dayBinder = TimetableDayBinder(daySize.height) { selectDate(it.date) }
            monthHeaderBinder = TimetableMonthHeaderBinder(daysOfWeek)
            setMonthScrollListener()
        }

        binding.speedDialView.run {
            lawProvider.showIfYouHaveLaw(this)
            SpeedDialDelegate.addActions(this)

            setOnActionSelectedListener {
                when(it.id){
                    R.id.multipleClass -> viewModel.openScreenMultipleClass()
                    R.id.oneTimeClass -> viewModel.openScreenOneTimeClass()
                    R.id.note -> viewModel.openScreenNote()
                }
                false
            }
        }

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CompositeDelegateAdapter(
                MultipleClassesDelegateAdapter(this@TimetableFragment),
                OneTimeClassesDelegateAdapter(this@TimetableFragment)
            )
        }
    }

    private fun selectDate(date: LocalDate){
        val dayBinder = binding.calendarView.dayBinder as TimetableDayBinder

        if(date != dayBinder.selectedDate){
            val oldDate = dayBinder.selectedDate
            dayBinder.selectedDate = date
            binding.calendarView.notifyDateChanged(oldDate)
            binding.calendarView.notifyDateChanged(date)
            binding.dateTextView.text = selectionFormatter.format(date)
            viewModel.getClassesForSelectedDate(date)
        }
    }

    private fun collapseOrExpandCalendar(){
        binding.calendarView.monthScrollListener = {}

        isCollapse = !isCollapse

        val selectedDate = (binding.calendarView.dayBinder as TimetableDayBinder).selectedDate

        if(!isCollapse){
            binding.calendarView.updateMonthConfiguration(
                inDateStyle = InDateStyle.ALL_MONTHS,
                outDateStyle = OutDateStyle.END_OF_GRID,
                maxRowCount = 6,
                hasBoundaries = true,
            )
            binding.calendarView.scrollToMonth(selectedDate.yearMonth)
            binding.dateTextView.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_up),
                null
            )
        }else{
            binding.calendarView.updateMonthConfiguration(
                inDateStyle = InDateStyle.FIRST_MONTH,
                outDateStyle = OutDateStyle.END_OF_ROW,
                maxRowCount = 1,
                hasBoundaries = false
            )
            binding.calendarView.scrollToDate(selectedDate)
            binding.dateTextView.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_down),
                null
            )
        }
        selectDate(selectedDate)
        setMonthScrollListener()
    }

    private fun setMonthScrollListener(){
        binding.calendarView.postDelayed({
            binding.calendarView.monthScrollListener = {
                if(binding.calendarView.maxRowCount == 6){
                    if(it.yearMonth == LocalDate.now().yearMonth){
                        selectDate(LocalDate.now())
                    }else{
                        selectDate(it.yearMonth.atDay(1))
                    }
                }else{
                    selectDate(binding.calendarView.findFirstVisibleDay()!!.date)
                }
            }
        },500)

    }

    companion object{
        fun newInstance(): TimetableFragment = TimetableFragment()
    }
}