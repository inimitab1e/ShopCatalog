package com.example.shopcatalog.ui.autentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentLoginBinding
import com.example.shopcatalog.extensions.launchWhenResumed
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickers()
        initObservers()
    }

    private fun initClickers() {
        with(binding) {
            tvRegisterLink.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                doValidationAndLogin()
            }
        }
    }

    private fun doValidationAndLogin() {
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        loginViewModel.validateAndLogin(email, password)
    }

    private fun initObservers() {
        loginViewModel.errorLoginResponseMessage.onEach { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }.launchWhenResumed(lifecycleScope)

        loginViewModel.isLoginSuccess.onEach { isLoginSuccess ->
            if (isLoginSuccess) {
                findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
            }
        }.launchWhenStarted(lifecycleScope)

        loginViewModel.errorValidationFormsMessage.onEach { validationState ->
            if (validationState.emailError != null) {
                with(binding.tvEmailErrorMessage) {
                    isVisible = true
                    text = validationState.emailError
                }
            } else {
                binding.tvEmailErrorMessage.isGone = true
            }

            if (validationState.passwordError != null) {
                with(binding.tvPasswordErrorMessage) {
                    isVisible = true
                    text = validationState.passwordError
                }
            } else {
                binding.tvPasswordErrorMessage.isGone = true
            }
        }.launchWhenStarted(lifecycleScope)
    }
}