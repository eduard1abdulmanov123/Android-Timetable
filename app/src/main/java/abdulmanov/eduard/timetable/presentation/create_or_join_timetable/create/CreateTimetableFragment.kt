package abdulmanov.eduard.timetable.presentation.create_or_join_timetable.create

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentCreateTimetableBinding
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.presentation.App
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

class CreateTimetableFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CreateTimetableViewModel> { viewModelFactory }

    private var _binding: FragmentCreateTimetableBinding? = null
    private val binding: FragmentCreateTimetableBinding
        get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateTimetableBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(::showApplyProgress))
        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessage))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        binding.createTimetableToolbar.setTitle(R.string.create_timetable_create_title)

        binding.typeWeekRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.oddWeekRadioButton -> viewModel.currentSelectTypeWeek = TypeWeek.ODD
                R.id.evenWeekRadioButton -> viewModel.currentSelectTypeWeek = TypeWeek.EVEN
            }
        }

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

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = CreateTimetableFragment()
    }
}