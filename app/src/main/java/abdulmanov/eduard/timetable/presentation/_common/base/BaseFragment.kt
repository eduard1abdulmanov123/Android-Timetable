package abdulmanov.eduard.timetable.presentation._common.base

import abdulmanov.eduard.timetable.presentation._common.provides.LawProvider
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jintin.bindingextension.BindingFragment
import javax.inject.Inject

open class BaseFragment<V: ViewBinding>: BindingFragment<V>() {

    @Inject
    lateinit var lawProvider: LawProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected inline fun <reified VM: ViewModel> initViewModel() = viewModels<VM> { viewModelFactory }

    protected fun showMessageAsToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}