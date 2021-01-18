package abdulmanov.eduard.timetable.presentation.events.note

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentNoteBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.timetable.presentation.events.dialogs.DatePickerBottomSheetDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NoteFragment: Fragment(), DatePickerBottomSheetDialog.DatePickerCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<NoteViewModel> { viewModelFactory }

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding
        get() = _binding!!

    private val selectionFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onChangeDate(date: LocalDate?) {
        if(date == null){
            binding.dateTextInputEditText.setText("")
        }else{
            binding.dateTextInputEditText.setText(selectionFormatter.format(date))
        }
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

        binding.applyContainer.setOnClickListener {

        }
    }

    private fun openDatePicker(){
        val dialog = DatePickerBottomSheetDialog.newInstance(binding.dateTextInputEditText.text?.toString())
        dialog.show(childFragmentManager, DatePickerBottomSheetDialog.TAG)
    }

    companion object{
        fun newInstance() = NoteFragment()
    }
}