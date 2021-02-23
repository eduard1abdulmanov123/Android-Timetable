package abdulmanov.eduard.timetable.presentation

import abdulmanov.eduard.timetable.dagger.components.AppComponent
import abdulmanov.eduard.timetable.dagger.components.DaggerAppComponent
import android.app.Application
import io.reactivex.plugins.RxJavaPlugins

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}