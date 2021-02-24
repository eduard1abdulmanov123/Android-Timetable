package abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.helpers

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.ItemDatePickerCalendarDayBinding
import abdulmanov.eduard.timetable.presentation._common.extensions.setTextColorRes
import android.view.View
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class MultipleDatePickerDayBinder(
    private val size: Int,
    private val clickListener: (CalendarDay) -> Unit
) : DayBinder<MultipleDatePickerDayBinder.DayViewContainer> {

    var selectedDates: MutableList<LocalDate> = mutableListOf()

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
            day.date in selectedDates -> {
                container.textView.setBackgroundResource(R.drawable.bg_selected_day)
                container.textView.setTextColorRes(R.color.colorWhite)
            }
            day.date == LocalDate.now() -> {
                container.textView.setBackgroundResource(R.drawable.bg_current_day)
                container.textView.setTextColorRes(R.color.colorAccent)
            }
            else -> {
                container.textView.background = null
                container.textView.setTextColorRes(R.color.colorTextPrimary)
            }
        }
    }

    class DayViewContainer(view: View, size:Int, clickListener: (CalendarDay) -> Unit): ViewContainer(view) {

        val textView = (ItemDatePickerCalendarDayBinding.bind(view).oneDayFrameLayout.getChildAt(0) as TextView)

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