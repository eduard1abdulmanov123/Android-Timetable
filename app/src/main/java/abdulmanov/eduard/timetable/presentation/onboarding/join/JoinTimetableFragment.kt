package abdulmanov.eduard.timetable.presentation.onboarding.join

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentJoinTimetableBinding
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

class JoinTimetableFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<JoinTimetableViewModel> { viewModelFactory }

    private var _binding: FragmentJoinTimetableBinding? = null
    private val binding: FragmentJoinTimetableBinding
        get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJoinTimetableBinding.inflate(inflater, container,false)
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

    private fun initUI() {
        binding.joinTimetableToolbar.setTitle(R.string.join_timetable_join_title)

        binding.applyContainer.setOnClickListener {
            viewModel.joinTimetable(binding.linkTimetableTextInputEditText.text.toString())
        }

        binding.createTimetableTextView.setOnClickListener {
            viewModel.openScreenCreateTimetable()
        }
    }

    private fun showApplyProgress(show:Boolean){
        binding.applyTextView.isVisible = !show
        binding.applyProgressBar.isVisible = show
    }

    private fun showErrorMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = JoinTimetableFragment()
    }
}