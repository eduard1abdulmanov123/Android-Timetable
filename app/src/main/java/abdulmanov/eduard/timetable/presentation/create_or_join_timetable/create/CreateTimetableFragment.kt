package abdulmanov.eduard.timetable.presentation.create_or_join_timetable.create

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentCreateTimetableBinding
import abdulmanov.eduard.timetable.domain.models.Periodicity
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class CreateTimetableFragment: BaseFragment<FragmentCreateTimetableBinding>() {

    private val viewModel by initViewModel<CreateTimetableViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(::showApplyProgress))
        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessageAsToast))
    }

    private fun initUI() {
        binding.createTimetableToolbar.setTitle(R.string.create_timetable_create_title)

        binding.typeWeekRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.oddWeekRadioButton -> viewModel.currentSelectTypeWeek = TypeWeek.ODD
                R.id.evenWeekRadioButton -> viewModel.currentSelectTypeWeek = TypeWeek.EVEN
            }
        }

        binding.timeZoneTextView.run {
            val items = context.resources.getStringArray(R.array.time_zone)
            val adapter = ArrayAdapter(context, R.layout.item_spinner, items)
            setAdapter(adapter)
        }
        binding.timeZoneTextView.addTextChangedListener { viewModel.currentSelectTimeZone = it.toString() }

        binding.applyContainer.setOnClickListener {
            viewModel.createTimetable()
        }

        binding.joinTimetableTextView.setOnClickListener {
            viewModel.openScreenJoinTimetable()
        }
    }

    private fun showApplyProgress(show:Boolean){
        binding.applyTextView.isVisible = !show
        binding.applyProgressBar.isVisible = show
    }

    companion object {
        fun newInstance() = CreateTimetableFragment()
    }
}