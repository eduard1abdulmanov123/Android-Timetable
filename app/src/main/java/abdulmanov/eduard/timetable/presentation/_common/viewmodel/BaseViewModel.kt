package abdulmanov.eduard.timetable.presentation._common.viewmodel

import abdulmanov.eduard.timetable.domain.interactors.AuthInteractor
import abdulmanov.eduard.timetable.presentation.Screens
import abdulmanov.eduard.timetable.presentation._common.extensions.getCode
import abdulmanov.eduard.timetable.presentation._common.extensions.getStatus
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException

@Suppress("PropertyName")
abstract class BaseViewModel() : ViewModel() {

    protected abstract val router: Router
    protected abstract val authInteractor: AuthInteractor

    protected val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun <T> Single<T>.safeSubscribe(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        val disposable = subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
    }

    protected fun Completable.safeSubscribe(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val disposable = subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
    }

    protected fun <T> Single<T>.addDispatchers(): Single<T> {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    protected fun Completable.addDispatchers(): Completable {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }

    protected fun onError(error:Throwable, action: () -> Unit = { _showMessageEvent.value = error.message.toString() }) {
        if(error.getCode() == 401 || error.getStatus() == "user_is_not_connect"){
            logout()
        } else {
            action()
        }
    }

    protected fun logout(){
        authInteractor.logout()
            .addDispatchers()
            .subscribe { router.replaceScreen(Screens.login()) }
            .connect()
    }
}