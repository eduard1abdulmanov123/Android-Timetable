package abdulmanov.eduard.timetable.presentation.events.onetimeclass

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.OneTimeClassInteractor
import abdulmanov.eduard.timetable.presentation._common.provides.StringProvider
import abdulmanov.eduard.timetable.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.timetable.presentation.events.onetimeclass.models.OneTimeClassPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import io.reactivex.Completable
import javax.inject.Inject

class OneTimeClassViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val oneTimeClassInteractor: OneTimeClassInteractor
): BaseViewModel() {

    private val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    lateinit var oneTimeClass: OneTimeClassPresentationModel
        private set

    fun onBackCommandClick() = router.exit()

    fun setOneTimeClassOrDefault(oneTimeClass: OneTimeClassPresentationModel?) {
        this.oneTimeClass = oneTimeClass ?: OneTimeClassPresentationModel()
    }

    fun getTitleForToolbar(): String{
        return stringProvider.getString(if(oneTimeClass.isNew()) R.string.one_time_class_new_class else R.string.one_time_class_edit_class)
    }

    fun getTextForApplyButton(): String {
        return stringProvider.getString(if(oneTimeClass.isNew()) R.string.one_time_class_create else R.string.one_time_class_save)
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

    fun delete(){
        oneTimeClassInteractor.deleteOneTimeClass(oneTimeClass.id)
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
        val domainModel = OneTimeClassPresentationModel.toDomain(oneTimeClass)

        return if(oneTimeClass.isNew()){
            oneTimeClassInteractor.createOneTimeClass(domainModel)
        }else{
            oneTimeClassInteractor.updateOneTimeClass(domainModel.id, domainModel)
        }
    }
}