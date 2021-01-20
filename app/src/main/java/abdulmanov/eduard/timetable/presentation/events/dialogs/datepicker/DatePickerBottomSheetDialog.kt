package abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.BottomDialogDatePickerBinding
import abdulmanov.eduard.timetable.presentation._common.extensions.daysOfWeekFromLocale
import abdulmanov.eduard.timetable.presentation._common.extensions.dpToPx
import abdulmanov.eduard.timetable.presentation._common.extensions.getScreenSize
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.helpercalendar.DatePickerDayBinder
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.helpercalendar.DatePickerMonthHeaderBinder
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
import java.time.format.DateTimeFormatter

class DatePickerBottomSheetDialog: BottomSheetDialogFragment() {

    private var _binding: BottomDialogDatePickerBinding? = null
    private val binding: BottomDialogDatePickerBinding
        get() = _binding!!

    private var callback: DatePickerCallback? = null

    private val currentSelectedDateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")
    private val selectedDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = parentFragment as DatePickerCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomDialogDatePickerBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

    }

    private fun initUI(){
        binding.throwOffTextView.setOnClickListener {
            selectDate(null)
            closeDatePicker()
        }

        binding.applyTextView.setOnClickListener {
            closeDatePicker()
        }

        binding.calendarView.run {
            val screenSize = context.getScreenSize()
            daySize = Size(((screenSize.x -64.dpToPx())/7).toInt(),screenSize.x/10)

            val daysOfWeek = daysOfWeekFromLocale()
            val currentMonth = YearMonth.now()

            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToDate(LocalDate.now())
            dayBinder = DatePickerDayBinder(daySize.height) { selectDate(it.date) }
            monthHeaderBinder = DatePickerMonthHeaderBinder(daysOfWeek)

            requireArguments().getString(ARG_CURRENT_SELECTED_DATE, null)?.let {
                if(it.isNotEmpty()) {
                    selectDate(LocalDate.parse(it, currentSelectedDateFormatter))
                }
            }
        }
    }

    private fun selectDate(date: LocalDate?){
        val dayBinder = binding.calendarView.dayBinder as DatePickerDayBinder

        if(date != dayBinder.selectedDate){
            val oldDate = dayBinder.selectedDate
            dayBinder.selectedDate = date
            oldDate?.let { binding.calendarView.notifyDateChanged(oldDate) }
            date?.let { binding.calendarView.notifyDateChanged(date) }

            if(date != null) {
                binding.selectedDateTextView.text = selectedDateFormatter.format(date)
            }else{
                binding.selectedDateTextView.setText(R.string.date_picker_select_date)
            }
        }
    }

    private fun closeDatePicker(){
        val dayBinder = binding.calendarView.dayBinder as DatePickerDayBinder
        callback?.onChangeDate(dayBinder.selectedDate)
        dismiss()
    }

    companion object{
        const val TAG = "DatePickerBottomSheetDialog"

        private const val ARG_CURRENT_SELECTED_DATE = "CURRENT_SELECTED_DATE"

        fun newInstance(date: String?): DatePickerBottomSheetDialog {
            return DatePickerBottomSheetDialog().apply {
                arguments = bundleOf(ARG_CURRENT_SELECTED_DATE to date)
            }
        }
    }

    interface DatePickerCallback {
        fun onChangeDate(date: LocalDate?)
    }
}