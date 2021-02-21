package abdulmanov.eduard.timetable.presentation._common.base

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.jintin.bindingextension.BindingActivity
import javax.inject.Inject

abstract class BaseActivity<V: ViewBinding>: BindingActivity<V>()  {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    protected abstract val navigator: AppNavigator

    protected inline fun <reified VM: ViewModel> initViewModel() = viewModels<VM> { viewModelFactory }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    protected fun showMessageAsToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}