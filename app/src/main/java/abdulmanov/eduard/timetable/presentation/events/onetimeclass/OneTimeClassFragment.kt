package abdulmanov.eduard.timetable.presentation.events.onetimeclass

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentOneTimeClassBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.timetable.presentation._common.extensions.bind
import abdulmanov.eduard.timetable.presentation.events.dialogs.datepicker.DatePickerBottomSheetDialog
import abdulmanov.eduard.timetable.presentation.events.dialogs.timepicker.TimePickerBottomSheetDialog
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

class OneTimeClassFragment: BaseFragment<FragmentOneTimeClassBinding>(),
    TimePickerBottomSheetDialog.TimePickerCallback,
    DatePickerBottomSheetDialog.DatePickerCallback {

    private val viewModel by initViewModel<OneTimeClassViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setOneTimeClassOrDefault(requireArguments().getParcelable(ARG_ONE_TIME_CLASS))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessage))
        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(::showApplyProgress))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteItem -> viewModel.delete()
        }
        return true
    }

    override fun onChangeTime(time: String, viewId: Int) {
        when(viewId){
            binding.startTextInputEditText.id -> binding.startTextInputEditText.setText(time)
            binding.endTextInputEditText.id -> binding.endTextInputEditText.setText(time)
        }
    }

    override fun onChangeDate(date: String) {
        binding.dateTextInputEditText.setText(date)
    }

    private fun initUI() {
        initFields()
        initToolbar()
        initTimesFields()
        initDateField()
        initApplyButton()

        binding.subjectTextInputEditText.bind(viewModel.oneTimeClass::nameSubject)
        binding.teacherTextInputEditText.bind(viewModel.oneTimeClass::nameTeacher)
        binding.typeClassTextInputEditText.bind(viewModel.oneTimeClass::typeClass)
        binding.audienceTextInputEditText.bind(viewModel.oneTimeClass::audience)
        binding.startTextInputEditText.bind(viewModel.oneTimeClass::startOfClass)
        binding.endTextInputEditText.bind(viewModel.oneTimeClass::endOfClass)
        binding.dateTextInputEditText.bind(viewModel.oneTimeClass::dateOfClass)
    }

    private fun initFields(){
        viewModel.oneTimeClass.run {
            binding.subjectTextInputEditText.setText(nameSubject)
            binding.teacherTextInputEditText.setText(nameTeacher)
            binding.typeClassTextInputEditText.setText(typeClass)
            binding.audienceTextInputEditText.setText(audience)
            binding.startTextInputEditText.setText(startOfClass)
            binding.endTextInputEditText.setText(endOfClass)
            binding.dateTextInputEditText.setText(dateOfClass)
        }
    }

    private fun initToolbar(){
        binding.oneTimeClassToolbar.run {
            title = viewModel.getTitleForToolbar()
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
            inflateMenu(R.menu.menu_one_time_class)
            setOnMenuItemClickListener(this@OneTimeClassFragment::onOptionsItemSelected)
            menu.findItem(R.id.deleteItem).isVisible = !viewModel.oneTimeClass.isNew()
        }
    }

    private fun initTimesFields(){
        fun openTimePicker(time: String?, viewId: Int){
            val dialog = TimePickerBottomSheetDialog.newInstance(time, viewId)
            dialog.show(childFragmentManager, TimePickerBottomSheetDialog.TAG)
        }

        binding.startTextInputEditText.setOnClickListener {
            openTimePicker(
                binding.startTextInputEditText.text?.toString(),
                binding.startTextInputEditText.id
            )
        }

        binding.endTextInputEditText.setOnClickListener {
            openTimePicker(
                binding.endTextInputEditText.text?.toString(),
                binding.endTextInputEditText.id
            )
        }
    }

    private fun initDateField(){
        binding.dateTextInputEditText.setOnClickListener {
            val dialog = DatePickerBottomSheetDialog.newInstance(binding.dateTextInputEditText.text?.toString())
            dialog.show(childFragmentManager, DatePickerBottomSheetDialog.TAG)
        }
    }

    private fun initApplyButton() {
        binding.applyTextView.text = viewModel.getTextForApplyButton()
        binding.applyContainer.setOnClickListener {
            viewModel.createOrUpdate()
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showApplyProgress(show:Boolean){
        binding.applyTextView.isVisible = !show
        binding.applyProgressBar.isVisible = show
    }

    companion object{
        private const val ARG_ONE_TIME_CLASS = "ONE_TIME_CLASS"

        fun newInstance(oneTimeClass: OneTimeClassPresentationModel?): OneTimeClassFragment {
            return OneTimeClassFragment().apply {
                arguments = bundleOf(ARG_ONE_TIME_CLASS to oneTimeClass)
            }
        }
    }
}