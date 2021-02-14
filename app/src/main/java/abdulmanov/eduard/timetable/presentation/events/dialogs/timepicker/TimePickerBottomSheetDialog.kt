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

            val currentTime = if(savedInstanceState == null){
                requireArguments().getString(ARG_CURRENT_SELECTED_TIME, null)
            }else{
                savedInstanceState.getString(ARG_CURRENT_SELECTED_TIME, null)
            }
            TimePickerDelegate.setTime(binding.timePicker, currentTime)
        }

        binding.throwOffTextView.setOnClickListener {
            TimePickerDelegate.setTime(binding.timePicker,null)
            callback?.onChangeTime("", requireArguments().getInt(ARG_VIEW_ID))
            dismiss()
        }

        binding.applyTextView.setOnClickListener {
            callback?.onChangeTime(
                TimePickerDelegate.getTime(binding.timePicker),
                requireArguments().getInt(ARG_VIEW_ID)
            )
            dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_CURRENT_SELECTED_TIME, TimePickerDelegate.getTime(binding.timePicker))
    }

    companion object{
        const val TAG = "TimePickerBottomSheetDialog"

        private const val ARG_CURRENT_SELECTED_TIME = "CURRENT_SELECTED_TIME"
        private const val ARG_VIEW_ID = "VIEW_ID"

        fun newInstance(time: String?, viewId: Int): TimePickerBottomSheetDialog {
            return TimePickerBottomSheetDialog().apply {
                arguments = bundleOf(ARG_CURRENT_SELECTED_TIME to time, ARG_VIEW_ID to viewId)
            }
        }
    }

    interface TimePickerCallback{
        fun onChangeTime(time: String, viewId: Int)
    }
}