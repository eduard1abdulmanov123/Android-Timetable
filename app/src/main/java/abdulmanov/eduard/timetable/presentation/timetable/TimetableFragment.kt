package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentTimetableBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.extensions.daysOfWeekFromLocale
import abdulmanov.eduard.timetable.presentation._common.extensions.getScreenSize
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.yearMonth
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TimetableFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentTimetableBinding? = null
    private val binding: FragmentTimetableBinding
        get() = _binding!!

    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    private var isCollapse = true
    private var isFirstCalendarShow = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimetableBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI(){
        binding.dateTextView.text = selectionFormatter.format(LocalDate.now())
        binding.dateTextView.setOnClickListener {
            collapseOrExpandCalendar()
        }

        binding.calendarView.run {
            val screenSize = context.getScreenSize()
            daySize = Size(screenSize.x/7,screenSize.x/10)

            val daysOfWeek = daysOfWeekFromLocale()
            val currentMonth = YearMonth.now()

            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToDate(LocalDate.now())
            dayBinder = CustomDayBinder(daySize.height) { selectDate(it.date) }
            monthHeaderBinder = CustomMonthHeaderBinder(daysOfWeek)
            setMonthScrollListener()
        }
    }

    private fun selectDate(date: LocalDate){
        val dayBinder = binding.calendarView.dayBinder as CustomDayBinder

        if(date != dayBinder.selectedDate){
            val oldDate = dayBinder.selectedDate
            dayBinder.selectedDate = date
            binding.calendarView.notifyDateChanged(oldDate)
            binding.calendarView.notifyDateChanged(date)
            binding.dateTextView.text = selectionFormatter.format(date)
        }
    }

    private fun collapseOrExpandCalendar(){
        binding.calendarView.monthScrollListener = {}

        isCollapse = !isCollapse

        val selectedDate = (binding.calendarView.dayBinder as CustomDayBinder).selectedDate

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
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_keyboard_arrow_up),
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
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_keyboard_arrow_down),
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