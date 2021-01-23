package abdulmanov.eduard.timetable.presentation.events.note

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentNoteBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.DatePickerBottomSheetDialog
import abdulmanov.eduard.timetable.presentation.events.dialogs.timepicker.TimePickerBottomSheetDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class NoteFragment: Fragment(), DatePickerBottomSheetDialog.DatePickerCallback, TimePickerBottomSheetDialog.TimePickerCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<NoteViewModel> { viewModelFactory }

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding
        get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(::showApplyProgress))
        viewModel.showMessageErrorEvent.observe(viewLifecycleOwner, Observer(::showErrorMessage))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onChangeDate(date: String) {
        binding.dateTextInputEditText.setText(date)
    }

    override fun onChangeTime(time: String) {
        binding.timeTextInputEditText.setText(time)
    }

    private fun initUI() {
        binding.noteToolbar.run {
            setTitle(R.string.note_new_note)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        binding.dateTextInputEditText.setOnClickListener {
            openDatePicker()
        }

        binding.timeTextInputEditText.setOnClickListener {
            openTimePicker()
        }

        binding.applyContainer.setOnClickListener {
            viewModel.createNote(
                binding.contentTextInputEditText.text.toString(),
                binding.dateTextInputEditText.text.toString(),
                binding.timeTextInputEditText.text.toString(),
                binding.groupNoteCheckBox.isChecked
            )
        }
    }

    private fun openDatePicker(){
        val dialog = DatePickerBottomSheetDialog.newInstance(binding.dateTextInputEditText.text?.toString())
        dialog.show(childFragmentManager, DatePickerBottomSheetDialog.TAG)
    }

    private fun openTimePicker(){
        val dialog = TimePickerBottomSheetDialog.newInstance(binding.timeTextInputEditText.text?.toString())
        dialog.show(childFragmentManager, TimePickerBottomSheetDialog.TAG)
    }

    private fun showApplyProgress(show:Boolean){
        binding.applyTextView.isVisible = !show
        binding.applyProgressBar.isVisible = show
    }

    private fun showErrorMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        fun newInstance() = NoteFragment()
    }
}