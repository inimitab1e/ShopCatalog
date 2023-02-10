package com.example.shopcatalog.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentRegisterBinding
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.errorRegistrationResponseMessage.onEach { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }.launchWhenStarted(lifecycleScope)

        registerViewModel.isRegistrationSuccess.onEach { isRegistrationSuccess ->
            if (isRegistrationSuccess) {
                findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
            }
        }.launchWhenStarted(lifecycleScope)

        with(binding) {
            tvLoginLink.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            btnCreateAccount.setOnClickListener {
                doRegistration()
            }
        }
    }

    private fun doRegistration() {
        val userName = binding.etProfileName.text.toString()
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()
        registerViewModel.doRegistration(userName, email, password)
    }
}