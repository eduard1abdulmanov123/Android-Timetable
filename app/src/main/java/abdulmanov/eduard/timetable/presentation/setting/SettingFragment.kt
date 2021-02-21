package abdulmanov.eduard.timetable.presentation.setting

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentSettingBinding
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import abdulmanov.eduard.timetable.presentation._common.provides.LawProvider
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import javax.inject.Inject

class SettingFragment: BaseFragment<FragmentSettingBinding>() {

    private val viewModel by initViewModel<SettingViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.changeTypeWeekEvent.observe(viewLifecycleOwner, Observer(::setCurrentSelectTypeWeek))
        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessageAsToast))
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

        lawProvider.showIfYouHaveLaw(binding.containerTypeWeekConstraintLayout)
        setCurrentSelectTypeWeek(viewModel.getCurrentSelectTypeWeek())
        binding.typeWeekRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.oddWeekRadioButton -> viewModel.setCurrentSelectTypeWeek(TypeWeek.ODD)
                R.id.evenWeekRadioButton -> viewModel.setCurrentSelectTypeWeek(TypeWeek.EVEN)
            }
        }

        lawProvider.showIfYouHaveLaw(binding.containerClearTimetableConstraintLayout)
        binding.containerClearTimetableConstraintLayout.setOnClickListener {
            viewModel.clearTimetable()
        }

        lawProvider.showIfYouHaveLaw(binding.containerSendLinkFeedbackConstraintLayout)
        binding.containerSendLinkFeedbackConstraintLayout.setOnClickListener {
            viewModel.onOpenScreenSendLink()
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

    companion object {
        fun newInstance() = SettingFragment()
    }
}