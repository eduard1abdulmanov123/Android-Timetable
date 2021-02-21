package abdulmanov.eduard.timetable.presentation._common.base

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jintin.bindingextension.BindingActivity
import javax.inject.Inject

open class BaseActivity<V: ViewBinding>: BindingActivity<V>()  {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected inline fun <reified VM: ViewModel> initViewModel() = viewModels<VM> { viewModelFactory }
}