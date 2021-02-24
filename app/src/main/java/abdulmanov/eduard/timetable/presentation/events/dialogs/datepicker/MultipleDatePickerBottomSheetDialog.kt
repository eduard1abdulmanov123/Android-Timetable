package abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.BottomDialogMultipleDatePickerBinding
import abdulmanov.eduard.timetable.presentation._common.extensions.dpToPx
import abdulmanov.eduard.timetable.presentation._common.extensions.getDaysOfWeekFromLocale
import abdulmanov.eduard.timetable.presentation._common.extensions.getMonthsForCalendar
import abdulmanov.eduard.timetable.presentation._common.extensions.getScreenSize
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.helpers.DatePickerDayBinder
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.helpers.DatePickerMonthHeaderBinder
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.helpers.MultipleDatePickerDayBinder
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendarview.utils.Size
import java.time.LocalDate
import java.time.YearMonth

class MultipleDatePickerBottomSheetDialog: BottomSheetDialogFragment() {

    private var _binding: BottomDialogMultipleDatePickerBinding? = null
    private val binding: BottomDialogMultipleDatePickerBinding
        get() = _binding!!

    private var callback: MultipleDatePickerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as MultipleDatePickerCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomDialogMultipleDatePickerBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.run {
            val screenSize = context.getScreenSize()
            daySize = Size(((screenSize.x -64.dpToPx())/7).toInt(),screenSize.x/10)

            val daysOfWeek = getDaysOfWeekFromLocale()
            val monthForCalendar = getMonthsForCalendar()

            setup(monthForCalendar.first, monthForCalendar.second, daysOfWeek.first())
            dayBinder = MultipleDatePickerDayBinder(daySize.height) { selectDate(it.date) }
            monthHeaderBinder = DatePickerMonthHeaderBinder(daysOfWeek)

            val datesStr = requireArguments().getString(ARG_CURRENT_SELECTED_DATES)

            if(!datesStr.isNullOrEmpty()){
                datesStr.split("\n")
                    .map { DatePickerDelegate.getDateAsLocalDate(it) }
                    .forEach { selectDate(it) }
            }
            scrollToMonth(YearMonth.now())
        }

        binding.throwOffTextView.setOnClickListener {
            val dayBinder = binding.calendarView.dayBinder as MultipleDatePickerDayBinder
            dayBinder.selectedDates.clear()
            binding.calendarView.notifyCalendarChanged()
            callback?.onChangeDates("")
            dismiss()
        }

        binding.applyTextView.setOnClickListener {
            val dayBinder = binding.calendarView.dayBinder as MultipleDatePickerDayBinder
            val dates = dayBinder.selectedDates.joinToString("\n", "", "") {
                DatePickerDelegate.getDateAsString(it)
            }
            callback?.onChangeDates(dates)
            dismiss()
        }
    }

    private fun selectDate(date: LocalDate){
        val dayBinder = binding.calendarView.dayBinder as MultipleDatePickerDayBinder

        if(date in dayBinder.selectedDates){
            dayBinder.selectedDates.remove(date)
        } else {
            dayBinder.selectedDates.add(date)
        }
        binding.calendarView.notifyDateChanged(date)
    }

    companion object {
        const val TAG = "MultipleDatePickerBottomSheetDialog"

        private const val ARG_CURRENT_SELECTED_DATES = "CURRENT_SELECTED_DATES"

        fun newInstance(dates: String): MultipleDatePickerBottomSheetDialog {
            return MultipleDatePickerBottomSheetDialog().apply {
                arguments = bundleOf(ARG_CURRENT_SELECTED_DATES to dates)
            }
        }
    }

    interface MultipleDatePickerCallback {
        fun onChangeDates(dates: String)
    }
}