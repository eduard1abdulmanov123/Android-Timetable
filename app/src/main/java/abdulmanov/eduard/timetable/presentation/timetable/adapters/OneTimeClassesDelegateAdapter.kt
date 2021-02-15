package abdulmanov.eduard.timetable.presentation.timetable.adapters

import abdulmanov.eduard.timetable.databinding.ItemClassBinding
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import android.annotation.SuppressLint
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OneTimeClassesDelegateAdapter(
    private val clickListener: ClickListener
) : ViewBindingDelegateAdapter<OneTimeClassPresentationModel, ItemClassBinding>(ItemClassBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun ItemClassBinding.onBind(item: OneTimeClassPresentationModel) {
        root.setOnClickListener { clickListener.onOneTimeClassClick(item) }

        typeClassTextView.text = "${item.typeClass} (${item.audience})"
        timeTextView.text = "${item.startOfClass}-${item.endOfClass}"
        nameSubjectTextView.text = item.nameSubject
        nameTeacherTextView.text = item.nameTeacher
    }

    override fun isForViewType(item: Any) = item is OneTimeClassPresentationModel

    override fun OneTimeClassPresentationModel.getItemId() = id

    interface ClickListener {
        fun onOneTimeClassClick(oneTimeClass: OneTimeClassPresentationModel)
    }
}