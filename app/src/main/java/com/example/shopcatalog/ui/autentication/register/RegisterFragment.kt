package com.example.shopcatalog.ui.autentication.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentRegisterBinding
import com.example.shopcatalog.extensions.launchWhenResumed
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickers()
        initObservers()
    }

    private fun initClickers() {
        with(binding) {
            tvLoginLink.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            btnCreateAccount.setOnClickListener {
                doValidationAndRegistration()
            }
        }
    }

    private fun doValidationAndRegistration() {
        val userName = binding.etProfileName.text.toString()
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()

        registerViewModel.validateAndRegister(userName, email, password)
    }

    private fun initObservers() {
        registerViewModel.errorRegistrationResponseMessage.onEach { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }.launchWhenResumed(lifecycleScope)

        registerViewModel.isRegistrationSuccess.onEach { isRegistrationSuccess ->
            if (isRegistrationSuccess) {
                findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
            }
        }.launchWhenStarted(lifecycleScope)

        registerViewModel.errorValidationFormsMessage.onEach { validationState ->
            if (validationState.userNameError != null) {
                with(binding.tvUserNameErrorMessage) {
                    isVisible = true
                    text = validationState.userNameError
                }
            } else {
                binding.tvUserNameErrorMessage.isGone = true
            }

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