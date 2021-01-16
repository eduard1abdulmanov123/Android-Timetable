package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.databinding.FragmentTimetableBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.extensions.daysOfWeekFromLocale
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kizitonwose.calendarview.model.InDateStyle
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
    private var isCollapse = false
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
            val daysOfWeek = daysOfWeekFromLocale()
            val currentMonth = YearMonth.now()

            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
            dayBinder = CustomDayBinder { selectDate(it.date) }
            monthHeaderBinder = CustomMonthHeaderBinder(daysOfWeek)
            monthScrollListener = {
                /*if(!isFirstCalendarShow) {
                    selectDate(it.yearMonth.atDay(1))
                }else{
                    isFirstCalendarShow = false
                }*/
            }
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
        isCollapse = !isCollapse

        val selectedDate = (binding.calendarView.dayBinder as CustomDayBinder).selectedDate

        if(!isCollapse){
            binding.calendarView.updateMonthConfiguration(
                inDateStyle = InDateStyle.ALL_MONTHS,
                maxRowCount = 6,
                hasBoundaries = true
            )
            binding.calendarView.scrollToMonth(selectedDate.yearMonth)
        }else{
            binding.calendarView.updateMonthConfiguration(
                inDateStyle = InDateStyle.FIRST_MONTH,
                maxRowCount = 1,
                hasBoundaries = false
            )
            binding.calendarView.scrollToDate(selectedDate)
        }
        selectDate(selectedDate)
    }

    companion object{
        fun newInstance(): TimetableFragment = TimetableFragment()
    }
}