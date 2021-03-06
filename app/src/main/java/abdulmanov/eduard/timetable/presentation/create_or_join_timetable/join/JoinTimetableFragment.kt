package abdulmanov.eduard.timetable.presentation.create_or_join_timetable.join

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentJoinTimetableBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class JoinTimetableFragment: BaseFragment<FragmentJoinTimetableBinding>() {

    private val viewModel by initViewModel<JoinTimetableViewModel>()

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
        binding.joinTimetableToolbar.setTitle(R.string.join_timetable_join_title)

        val link = requireArguments().getString(ARG_LINK, "")
        binding.linkTimetableTextInputEditText.setText(link)
        binding.view.isVisible = link.isEmpty()
        binding.createTimetableTextView.isVisible = link.isEmpty()

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

    companion object {
        private const val ARG_LINK = "link"

        fun newInstance(link: String): JoinTimetableFragment{
            return JoinTimetableFragment().apply {
                arguments = bundleOf(ARG_LINK to link)
            }
        }
    }
}