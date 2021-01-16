package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.ItemCalendarDayBinding
import abdulmanov.eduard.timetable.presentation._common.extensions.setTextColorRes
import android.util.Log
import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class CustomDayBinder(
    private val clickListener: (CalendarDay) -> Unit
) : DayBinder<CustomDayBinder.DayViewContainer>{

    var selectedDate: LocalDate = LocalDate.now()

    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view, clickListener)
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

    class DayViewContainer(view: View, clickListener: (CalendarDay) -> Unit): ViewContainer(view) {

        val textView = ItemCalendarDayBinding.bind(view).oneDayTextView

        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                if(day.owner == DayOwner.THIS_MONTH) {
                    clickListener.invoke(day)
                }
            }
        }
    }
}