package abdulmanov.eduard.timetable.presentation.timetable.helpers.caledar

import abdulmanov.eduard.timetable.databinding.ItemTimetableCalendarDayLegendBinding
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class TimetableMonthHeaderBinder(private val daysOfWeek: Array<DayOfWeek>) : MonthHeaderFooterBinder<TimetableMonthHeaderBinder.MonthViewContainer>{

    override fun create(view: View) = MonthViewContainer(view)

    override fun bind(container: MonthViewContainer, month: CalendarMonth) {
        if(container.legendLinearLayout.tag == null){
            container.legendLinearLayout.tag = month.yearMonth
            container.legendLinearLayout.children
                .map { it as TextView }
                .forEachIndexed { index, textView ->
                    var dayOfWeek = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    dayOfWeek = dayOfWeek.first().toUpperCase() + dayOfWeek.slice(1 until dayOfWeek.length)
                    textView.text = dayOfWeek
                }
        }
    }

    class MonthViewContainer(view: View): ViewContainer(view){
        val legendLinearLayout = ItemTimetableCalendarDayLegendBinding.bind(view).legendLinearLayout
    }
}