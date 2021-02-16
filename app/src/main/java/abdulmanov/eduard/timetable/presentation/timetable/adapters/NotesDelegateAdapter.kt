package abdulmanov.eduard.timetable.presentation.timetable.adapters

import abdulmanov.eduard.timetable.databinding.ItemNoteBinding
import abdulmanov.eduard.timetable.presentation.events.note.models.NotePresentationModel
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class NotesDelegateAdapter(
    private val clickListener: ClickListener
) : ViewBindingDelegateAdapter<NotePresentationModel, ItemNoteBinding>(ItemNoteBinding::inflate) {

    override fun ItemNoteBinding.onBind(item: NotePresentationModel) {
        root.setOnClickListener { clickListener.onNoteClick(item) }

        contentTextView.text = item.content
        timeTextView.text = item.time
    }

    override fun isForViewType(item: Any) = item is NotePresentationModel

    override fun NotePresentationModel.getItemId() = id

    interface ClickListener {
        fun onNoteClick(note: NotePresentationModel)
    }
}