package abdulmanov.eduard.timetable.presentation.events.multipleclass

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.MultipleClassInteractor
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.timetable.presentation.events.multipleclass.models.MultipleClassPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import io.reactivex.Completable
import javax.inject.Inject

class MultipleClassViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val multipleClassInteractor: MultipleClassInteractor
): BaseViewModel() {

    private val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    lateinit var multipleClass: MultipleClassPresentationModel
        private set

    fun onBackCommandClick() = router.exit()

    fun setMultipleClassOrDefault(multipleClass: MultipleClassPresentationModel?) {
        this.multipleClass = multipleClass ?: MultipleClassPresentationModel()
    }

    fun getTitleForToolbar(): String{
        return stringProvider.getString(if(multipleClass.isNew()) R.string.multiple_class_new_class else R.string.multiple_class_edit_class)
    }

    fun getTextForApplyButton(): String {
        return stringProvider.getString(if(multipleClass.isNew()) R.string.multiple_class_create else R.string.multiple_class_save)
    }

    fun createOrUpdate(){
        if(_showApplyProgress.value == false){
            getCompletableCreateOrUpdate()
                .addDispatchers()
                .doOnSubscribe { _showApplyProgress.value = true }
                .doOnTerminate { _showApplyProgress.value = false }
                .subscribe(
                    {
                        onBackCommandClick()
                    },
                    {
                        _showMessageEvent.value = it.message.toString()
                    }
                )
                .connect()
        }
    }

    fun delete() {
        multipleClassInteractor.deleteMultipleClass(multipleClass.id)
            .safeSubscribe(
                {
                    onBackCommandClick()
                },
                {
                    _showMessageEvent.value = it.message.toString()
                }
            )
    }

    private fun getCompletableCreateOrUpdate(): Completable {
        val domainModel = MultipleClassPresentationModel.toDomain(multipleClass)

        return if(multipleClass.isNew()){
            multipleClassInteractor.createMultipleClass(domainModel)
        }else{
            multipleClassInteractor.updateMultipleClass(domainModel.id, domainModel)
        }
    }
}