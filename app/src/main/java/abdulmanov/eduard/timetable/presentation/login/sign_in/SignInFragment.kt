package abdulmanov.eduard.timetable.presentation.login.sign_in

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.databinding.FragmentSignInBinding
import abdulmanov.eduard.timetable.presentation.App
import abdulmanov.eduard.timetable.presentation._common.extensions.focus
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SignInFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SignInViewModel> { viewModelFactory }

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showMessageErrorEvent.observe(viewLifecycleOwner, Observer(::showMessageError))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI(){
        binding.passwordTextInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                signIn()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.entryTextView.setOnClickListener {
            signIn()
        }

        binding.registerNowTextView.setOnClickListener {
            viewModel.openSignUp()
        }
    }

    private fun signIn(){
        val login = binding.loginTextInputEditText.text.toString()
        binding.loginTextInputEditText.focus(false)

        val password = binding.passwordTextInputEditText.text.toString()
        binding.passwordTextInputEditText.focus(false)

        viewModel.signIn(login, password)
    }

    private fun showMessageError(show:Boolean){
        if(show){
            binding.messageErrorLinearLayout.visibility = View.VISIBLE
        }else{
            binding.messageErrorLinearLayout.visibility = View.GONE
        }
    }

    companion object{
        fun newInstance() = SignInFragment()
    }
}