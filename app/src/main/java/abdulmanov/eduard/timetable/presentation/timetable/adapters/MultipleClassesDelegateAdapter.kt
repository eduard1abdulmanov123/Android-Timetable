package abdulmanov.eduard.timetable.presentation.timetable.adapters

import abdulmanov.eduard.timetable.databinding.ItemMultipleClassBinding
import abdulmanov.eduard.timetable.presentation.timetable.models.MultipleClassPresentationModel
import android.annotation.SuppressLint
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class MultipleClassesDelegateAdapter(
    private val clickListener: ClickListener
) : ViewBindingDelegateAdapter<MultipleClassPresentationModel, ItemMultipleClassBinding>(ItemMultipleClassBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun ItemMultipleClassBinding.onBind(item: MultipleClassPresentationModel) {
        root.setOnClickListener { clickListener.onMultipleClassClick(item) }

        typeClassTextView.text = "${item.typeClass} (${item.audience})"
        timeTextView.text = "${item.startOfClass}-${item.endOfClass}"
        nameSubjectTextView.text = item.nameSubject
        nameTeacherTextView.text = item.nameTeacher
    }

    override fun isForViewType(item: Any) = item is MultipleClassPresentationModel

    override fun MultipleClassPresentationModel.getItemId() = id

    interface ClickListener {
        fun onMultipleClassClick(multipleClass: MultipleClassPresentationModel)
    }
}