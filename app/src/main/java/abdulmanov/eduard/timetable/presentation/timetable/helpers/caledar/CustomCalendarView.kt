package abdulmanov.eduard.timetable.presentation.timetable.helpers.caledar

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.presentation._common.extensions.getScreenSize
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.yearMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

//TODO при смене конфигурации вызывается monthScrollListener - исправить
@Suppress("UsePropertyAccessSyntax")
class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var selectedListener: ((LocalDate) -> Unit)? = null
    var collapseListener: ((Boolean) -> Unit)? = null

    val dateTextView: TextView

    private val calendarView: CalendarView
    private val dayBinder get() = calendarView.dayBinder as TimetableDayBinder

    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    private var isCollapse = true

    init {
        val view = inflate(context, R.layout.layout_calendar_view, this)
        calendarView = view.findViewById(R.id.calendarView) as CalendarView
        dateTextView = view.findViewById(R.id.dateTextView) as TextView
    }

    fun initialize(daysOfWeek: Array<DayOfWeek>, monthsForCalendar: Pair<YearMonth, YearMonth>, ){
        calendarView.run {
            daySize = context.getScreenSize().run { Size(x/7,x/10) }
            dayBinder = TimetableDayBinder(daySize.height, selectedListener)
            monthHeaderBinder = TimetableMonthHeaderBinder(daysOfWeek)
            setup(monthsForCalendar.first, monthsForCalendar.second, daysOfWeek.first())
            post { setMonthScrollListener() }
        }

        dateTextView.setOnClickListener {
            collapseListener?.invoke(!isCollapse)
        }
    }

    fun scrollToDate(date: LocalDate) = calendarView.scrollToDate(date)

    fun selectDate(date: LocalDate){
        if(date != dayBinder.selectedDate){
            val oldDate = dayBinder.selectedDate
            dayBinder.selectedDate = date
            calendarView.notifyDateChanged(oldDate)
            calendarView.notifyDateChanged(date)
            dateTextView.text = date.format(selectionFormatter)
        }
    }

    fun collapseOrExpandCalendar(isCollapse: Boolean){
        this.isCollapse = isCollapse

        dontCallMonthScrollListener {
            if(!this.isCollapse){
                calendarView.updateMonthConfiguration(
                    inDateStyle = InDateStyle.ALL_MONTHS,
                    outDateStyle = OutDateStyle.END_OF_GRID,
                    maxRowCount = 6,
                    hasBoundaries = true
                )
                calendarView.scrollToMonth(dayBinder.selectedDate.yearMonth)
            }else{
                calendarView.updateMonthConfiguration(
                    inDateStyle = InDateStyle.FIRST_MONTH,
                    outDateStyle = OutDateStyle.END_OF_ROW,
                    maxRowCount = 1,
                    hasBoundaries = false
                )
                calendarView.scrollToDate(dayBinder.selectedDate)
            }
        }

        updateArrowForDateTextView()
        selectDate(dayBinder.selectedDate)
    }

    private fun setMonthScrollListener(){
        calendarView.monthScrollListener = {
            if(calendarView.maxRowCount == 6){
                selectedListener?.invoke(it.yearMonth.atDay(1))
            }else{
                selectedListener?.invoke(calendarView.findFirstVisibleDay()!!.date)
            }
        }
    }

    //Хак для правильно поведения, при смене темы вызывается scrollMonth
    private fun dontCallMonthScrollListener(action: () -> Unit){
        calendarView.monthScrollListener = null
        action()
        post {
            calendarView.itemAnimator?.isRunning {
                setMonthScrollListener()
            }
        }
    }

    private fun updateArrowForDateTextView(){
        val imageRes = if(!isCollapse) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        val drawable = ContextCompat.getDrawable(context, imageRes)
        dateTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,null)
    }
}