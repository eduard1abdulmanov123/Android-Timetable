package abdulmanov.eduard.timetable.presentation.timetable

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentTimetableBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.base.BaseFragment
import abdulmanov.eduard.timetable.presentation._common.provides.LawProvider
import abdulmanov.eduard.timetable.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.timetable.presentation._common.extensions.getDaysOfWeekFromLocale
import abdulmanov.eduard.timetable.presentation._common.extensions.getMonthsForCalendar
import abdulmanov.eduard.timetable.presentation.timetable.adapters.MultipleClassesDelegateAdapter
import abdulmanov.eduard.timetable.presentation.timetable.helpers.speed_dial.SpeedDialDelegate
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import abdulmanov.eduard.timetable.presentation.events.note.models.NotePresentationModel
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import abdulmanov.eduard.timetable.presentation.timetable.adapters.NotesDelegateAdapter
import abdulmanov.eduard.timetable.presentation.timetable.adapters.OneTimeClassesDelegateAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import java.time.LocalDate
import javax.inject.Inject

class TimetableFragment: BaseFragment<FragmentTimetableBinding>(),
    MultipleClassesDelegateAdapter.ClickListener,
    OneTimeClassesDelegateAdapter.ClickListener,
    NotesDelegateAdapter.ClickListener {

    private val viewModel by initViewModel<TimetableViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(::onBackPressed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.selectedDate.observe(viewLifecycleOwner, Observer(::selectDate))
        viewModel.isCollapse.observe(viewLifecycleOwner, Observer(::setIsCollapse))
        viewModel.state.observe(viewLifecycleOwner, Observer(::setState))
        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessageAsToast))

        if(viewModel.selectedDate.value == null){
            viewModel.setSelectedDate(LocalDate.now())
        }
    }

    private fun onBackPressed(){
        if(binding.speedDialView.isOpen){
            binding.speedDialView.close()
        }else{
            viewModel.onBackCommandClick()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.openSettingItem -> viewModel.openScreenSetting()
        }
        return true
    }

    override fun onMultipleClassClick(multipleClass: MultipleClassPresentationModel) {
        if(lawProvider.haveAccess()) {
            viewModel.openScreenMultipleClass(multipleClass)
        }
    }

    override fun onOneTimeClassClick(oneTimeClass: OneTimeClassPresentationModel) {
        if(lawProvider.haveAccess()) {
            viewModel.openScreenOneTimeClass(oneTimeClass)
        }
    }

    override fun onNoteClick(note: NotePresentationModel) {
        if(lawProvider.haveAccessToNote(note)) {
            viewModel.openScreenNote(note)
        }
    }

    private fun initUI(){
        binding.timetableToolbar.run {
            setTitle(R.string.timetable_toolbar_title)
            inflateMenu(R.menu.menu_timetable)
            setOnMenuItemClickListener(this@TimetableFragment::onOptionsItemSelected)
        }

        binding.customCalendarView.run {
            selectedListener = viewModel::setSelectedDate
            collapseListener = viewModel::setIsCollapse
            initialize(getDaysOfWeekFromLocale(), getMonthsForCalendar())
        }

        binding.speedDialView.run {
            SpeedDialDelegate.addActions(lawProvider,this)

            setOnActionSelectedListener {
                when(it.id){
                    R.id.multipleClass -> viewModel.openScreenMultipleClass()
                    R.id.oneTimeClass -> viewModel.openScreenOneTimeClass()
                    R.id.note -> viewModel.openScreenNote()
                }
                false
            }
        }

        binding.swipeRefresh.setOnRefreshListener(viewModel::refresh)

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CompositeDelegateAdapter(
                MultipleClassesDelegateAdapter(this@TimetableFragment),
                OneTimeClassesDelegateAdapter(this@TimetableFragment),
                NotesDelegateAdapter(this@TimetableFragment)
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun selectDate(date: LocalDate){
        binding.customCalendarView.run {
            selectDate(date)
            scrollToDate(date)
            dateTextView.text =  "${dateTextView.text.split(",").first()}, ${viewModel.getTypeWeekForDate(date)}"
        }
        viewModel.getClassesForSelectedDate(date)
    }

    private fun setIsCollapse(isCollapse: Boolean){
        binding.customCalendarView.collapseOrExpandCalendar(isCollapse)
    }

    private fun setState(state: TimetableState){
        when(state){
            is TimetableState.Empty -> {
                binding.emptyItemsImageView.isVisible = true
                binding.swipeRefresh.isRefreshing = false
                binding.recyclerView.isVisible = false
            }
            is TimetableState.Data -> {
                binding.emptyItemsImageView.isVisible = false
                binding.swipeRefresh.isRefreshing = false
                binding.recyclerView.isVisible = true
                (binding.recyclerView.adapter as CompositeDelegateAdapter).swapData(state.items)
                binding.recyclerView.scrollToPosition(0)
            }
        }
    }

    companion object{
        fun newInstance(): TimetableFragment = TimetableFragment()
    }
}