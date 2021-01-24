package abdulmanov.eduard.timetable.presentation.setting

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentSettingBinding
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SettingFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SettingViewModel> { viewModelFactory }

    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.changeTypeWeekEvent.observe(viewLifecycleOwner, Observer(::setCurrentSelectTypeWeek))
        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessage))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        binding.settingToolbar.run {
            setTitle(R.string.setting_toolbar_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        binding.containerTimetableConstraintLayout.setOnClickListener {
            viewModel.openScreenCreateOrJoinTimetable()
        }

        setCurrentSelectTypeWeek(viewModel.getCurrentSelectTypeWeek())
        binding.typeWeekRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.oddWeekRadioButton -> viewModel.setCurrentSelectTypeWeek(TypeWeek.ODD)
                R.id.evenWeekRadioButton -> viewModel.setCurrentSelectTypeWeek(TypeWeek.EVEN)
            }
        }

        binding.containerFeedbackConstraintLayout.setOnClickListener {
            viewModel.onOpenScreenFeedback()
        }

        binding.exitSubtitleTextView.text = getString(R.string.setting_exit_subtitle, viewModel.getUserName())
        binding.containerExitConstraintLayout.setOnClickListener {
            viewModel.onOpenScreenLogin()
        }
    }

    private fun setCurrentSelectTypeWeek(typeWeek: TypeWeek){
        when(typeWeek){
            TypeWeek.ODD -> binding.oddWeekRadioButton.isChecked = true
            TypeWeek.EVEN -> binding.evenWeekRadioButton.isChecked = true
        }
        binding.typeWeekRadioGroup.jumpDrawablesToCurrentState()
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}