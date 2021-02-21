package abdulmanov.eduard.timetable.presentation.events.note

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentNoteBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.timetable.presentation._common.extensions.bind
import abdulmanov.eduard.timetable.presentation._common.provides.LawProvider
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.DatePickerBottomSheetDialog
import abdulmanov.eduard.timetable.presentation.events.dialogs.timepicker.TimePickerBottomSheetDialog
import abdulmanov.eduard.timetable.presentation.events.note.models.NotePresentationModel
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class NoteFragment: BaseFragment<FragmentNoteBinding>(),
    DatePickerBottomSheetDialog.DatePickerCallback,
    TimePickerBottomSheetDialog.TimePickerCallback {
    
    private val viewModel by initViewModel<NoteViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNoteOrDefault(requireArguments().getParcelable(ARG_NOTE))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessageAsToast))
        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(::showApplyProgress))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteItem -> viewModel.delete()
        }
        return true
    }

    override fun onChangeDate(date: String) {
        binding.dateTextInputEditText.setText(date)
    }

    override fun onChangeTime(time: String, viewId: Int) {
        binding.timeTextInputEditText.setText(time)
    }

    private fun initUI() {
        initFields()
        initToolbar()
        initDateField()
        initTimeField()
        initApplyButton()

        binding.contentTextInputEditText.bind(viewModel.note::content)
        binding.dateTextInputEditText.bind(viewModel.note::date)
        binding.timeTextInputEditText.bind(viewModel.note::time)
        binding.groupNoteCheckBox.bind(viewModel.note::visibility)

        lawProvider.showIfYouHaveLaw(binding.groupConstraintLayout)
    }

    private fun initFields() {
        viewModel.note.run {
            binding.contentTextInputEditText.setText(content)
            binding.dateTextInputEditText.setText(date)
            binding.timeTextInputEditText.setText(time)
            binding.groupNoteCheckBox.isChecked = visibility
        }
    }

    private fun initToolbar(){
        binding.noteToolbar.run {
            title = viewModel.getTitleForToolbar()
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
            inflateMenu(R.menu.menu_note)
            setOnMenuItemClickListener(this@NoteFragment::onOptionsItemSelected)
            menu.findItem(R.id.deleteItem).isVisible = !viewModel.note.isNew()
        }
    }

    private fun initDateField(){
        binding.dateTextInputEditText.setOnClickListener {
            val dialog = DatePickerBottomSheetDialog.newInstance(binding.dateTextInputEditText.text?.toString())
            dialog.show(childFragmentManager, DatePickerBottomSheetDialog.TAG)
        }
    }

    private fun initTimeField(){
        binding.timeTextInputEditText.setOnClickListener {
            val dialog = TimePickerBottomSheetDialog.newInstance(
                binding.timeTextInputEditText.text?.toString(),
                binding.timeTextInputEditText.id
            )
            dialog.show(childFragmentManager, TimePickerBottomSheetDialog.TAG)
        }
    }

    private fun initApplyButton() {
        binding.applyTextView.text = viewModel.getTextForApplyButton()
        binding.applyContainer.setOnClickListener {
            viewModel.createOrUpdate()
        }
    }
    
    private fun showApplyProgress(show:Boolean){
        binding.applyTextView.isVisible = !show
        binding.applyProgressBar.isVisible = show
    }

    companion object {
        private const val ARG_NOTE = "NOTE"

        fun newInstance(note: NotePresentationModel?): NoteFragment {
            return NoteFragment().apply {
                arguments = bundleOf(ARG_NOTE to note)
            }
        }
    }
}