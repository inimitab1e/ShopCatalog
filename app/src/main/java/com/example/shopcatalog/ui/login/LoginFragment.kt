package com.example.shopcatalog.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentLoginBinding
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.errorLoginResponseMessage.onEach { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }.launchWhenStarted(lifecycleScope)

        loginViewModel.isLoginSuccess.onEach { isLoginSuccess ->
            if (isLoginSuccess) {
                findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
            }
        }.launchWhenStarted(lifecycleScope)

        with(binding) {
            tvRegisterLink.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                doLogin()
            }
        }
    }

    private fun doLogin() {
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        loginViewModel.doLogin(email, password)
    }
}