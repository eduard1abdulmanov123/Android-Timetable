package abdulmanov.eduard.timetable.presentation.timetable.helpers.caledar

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.ItemTimetableCalendarDayBinding
import abdulmanov.eduard.timetable.presentation._common.extensions.setTextColorRes
import android.view.View
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class TimetableDayBinder(
    private val size: Int,
    private val clickListener: (CalendarDay) -> Unit
) : DayBinder<TimetableDayBinder.DayViewContainer>{

    var selectedDate: LocalDate = LocalDate.now()

    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view, size, clickListener)
    }

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.day = day
        container.textView.text = day.date.dayOfMonth.toString()

        when {
            day.owner != DayOwner.THIS_MONTH -> {
                container.textView.background = null
                container.textView.setTextColorRes(R.color.colorTextSecondary)
            }
            day.date == selectedDate -> {
                container.textView.setBackgroundResource(R.drawable.bg_selected_day)
                container.textView.setTextColorRes(R.color.colorWhite)
            }
            else -> {
                container.textView.background = null
                container.textView.setTextColorRes(R.color.colorTextPrimary)
            }
        }
    }

    class DayViewContainer(view: View, size:Int, clickListener: (CalendarDay) -> Unit): ViewContainer(view) {

        val textView = (ItemTimetableCalendarDayBinding.bind(view).oneDayFrameLayout.getChildAt(0) as TextView)

        lateinit var day: CalendarDay

        init {
            textView.updateLayoutParams {
                height = size
                width = size
            }

            view.setOnClickListener {
                if(day.owner == DayOwner.THIS_MONTH) {
                    clickListener.invoke(day)
                }
            }
        }
    }
}