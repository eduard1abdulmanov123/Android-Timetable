package abdulmanov.eduard.timetable.presentation.events.dialogs.timepicker

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.BottomDialogTimePickerBinding
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimePickerBottomSheetDialog: BottomSheetDialogFragment() {

    private var _binding: BottomDialogTimePickerBinding? = null
    private val binding: BottomDialogTimePickerBinding
        get() = _binding!!

    private var callback: TimePickerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as? TimePickerCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomDialogTimePickerBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timePicker.run {
            setIs24HourView(true)
            TimePickerDelegate.setTime(binding.timePicker, requireArguments().getString(ARG_TIME, null))
        }

        binding.throwOffTextView.setOnClickListener {
            TimePickerDelegate.setTime(binding.timePicker,null)
            callback?.onChangeTime("")
            dismiss()
        }

        binding.applyTextView.setOnClickListener {
            callback?.onChangeTime(TimePickerDelegate.getTime(binding.timePicker))
            dismiss()
        }
    }

    companion object{
        const val TAG = "TimePickerBottomSheetDialog"

        private const val ARG_TIME = "TIME"

        fun newInstance(time: String?): TimePickerBottomSheetDialog {
            return TimePickerBottomSheetDialog().apply {
                arguments = bundleOf(ARG_TIME to time)
            }
        }
    }

    interface TimePickerCallback{
        fun onChangeTime(time: String)
    }
}