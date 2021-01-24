package abdulmanov.eduard.timetable.presentation.login.sign_up

import abdulmanov.eduard.timetable.databinding.FragmentSignUpBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.extensions.focus
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SignUpFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SignUpViewModel> { viewModelFactory }

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showRegistrationInApp.observe(viewLifecycleOwner, Observer(::showRegistrationInApp))
        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessage))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI(){
        binding.passwordTextInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                signUp()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.registrationContainer.setOnClickListener {
            signUp()
        }

        binding.signInTextView.setOnClickListener {
            viewModel.openSignIn()
        }
    }

    private fun signUp(){
        val login = binding.loginTextInputEditText.text.toString()
        binding.loginTextInputEditText.focus(false)

        val password = binding.passwordTextInputEditText.text.toString()
        binding.passwordTextInputEditText.focus(false)

        viewModel.signUp(login, password)
    }

    private fun showRegistrationInApp(show:Boolean){
        binding.registrationTextView.isVisible = !show
        binding.registrationProgressBar.isVisible = show
    }

    private fun showMessage(show:Boolean){
        binding.messageErrorLinearLayout.isVisible = show
    }

    companion object{
        fun newInstance() = SignUpFragment()
    }
}