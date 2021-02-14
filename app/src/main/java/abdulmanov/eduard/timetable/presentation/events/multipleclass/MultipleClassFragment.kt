package abdulmanov.eduard.timetable.presentation.events.multipleclass

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentMultipleClassBinding
import abdulmanov.eduard.timetable.domain.models.Periodicity
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.timetable.presentation._common.extensions.getFullTitlesDaysOfWeekFromLocale
import abdulmanov.eduard.timetable.presentation._common.extensions.bind
import abdulmanov.eduard.timetable.presentation.events.dialogs.timepicker.TimePickerBottomSheetDialog
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

class MultipleClassFragment: BaseFragment<FragmentMultipleClassBinding>(), TimePickerBottomSheetDialog.TimePickerCallback {

    private val viewModel by initViewModel<MultipleClassViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setMultipleClassOrDefault(requireArguments().getParcelable(ARG_MULTIPLE_CLASS))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showMessageEvent.observe(viewLifecycleOwner,Observer(::showMessage))
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

    private fun initUI() {
        initFields()
        initToolbar()
        initSpinnersFields()
        initTimesFields()
        initApplyButton()

        binding.subjectTextInputEditText.bind(viewModel.multipleClass::nameSubject)
        binding.teacherTextInputEditText.bind(viewModel.multipleClass::nameTeacher)
        binding.typeClassTextInputEditText.bind(viewModel.multipleClass::typeClass)
        binding.audienceTextInputEditText.bind(viewModel.multipleClass::audience)
        binding.weekTextView.bind(viewModel.multipleClass::dayOfWeek)
        binding.startTextInputEditText.bind(viewModel.multipleClass::startOfClass)
        binding.endTextInputEditText.bind(viewModel.multipleClass::endOfClass)
        binding.periodicityTextView.bind(viewModel.multipleClass::periodicity)
    }

    private fun initFields() {
        viewModel.multipleClass.run {
            binding.subjectTextInputEditText.setText(nameSubject)
            binding.teacherTextInputEditText.setText(nameTeacher)
            binding.typeClassTextInputEditText.setText(typeClass)
            binding.audienceTextInputEditText.setText(audience)
            binding.weekTextView.setText(dayOfWeek)
            binding.startTextInputEditText.setText(startOfClass)
            binding.endTextInputEditText.setText(endOfClass)
            binding.periodicityTextView.setText(periodicity)
        }
    }

    private fun initToolbar() {
        binding.multipleClassToolbar.run {
            title = viewModel.getTitleForToolbar()
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
            inflateMenu(R.menu.menu_multiple_class)
            setOnMenuItemClickListener(this@MultipleClassFragment::onOptionsItemSelected)
            menu.findItem(R.id.deleteItem).isVisible = !viewModel.multipleClass.isNew()
        }
    }

    private fun initSpinnersFields() {
        binding.periodicityTextView.run {
            val items = Periodicity.values().map { it.title }
            val adapter = ArrayAdapter(context, R.layout.item_spinner, items)
            setAdapter(adapter)
        }

        binding.weekTextView.run {
            val items = getFullTitlesDaysOfWeekFromLocale()
            val adapter = ArrayAdapter(context, R.layout.item_spinner, items)
            setAdapter(adapter)
        }
    }

    private fun initTimesFields() {
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
        private const val ARG_MULTIPLE_CLASS = "MULTIPLE_CLASS"

        fun newInstance(multipleClass: MultipleClassPresentationModel?): MultipleClassFragment {
            return MultipleClassFragment().apply {
                arguments = bundleOf(ARG_MULTIPLE_CLASS to multipleClass)
            }
        }
    }
}