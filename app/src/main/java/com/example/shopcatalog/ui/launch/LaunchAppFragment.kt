package com.example.shopcatalog.ui.launch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shopcatalog.R
import com.example.shopcatalog.domain.utils.StringConstants
import com.example.shopcatalog.extensions.launchWhenResumed
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LaunchAppFragment : Fragment(R.layout.fragment_launch_app) {

    private val launchAppViewModel: LaunchAppViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAppViewModel.authState.onEach { authState ->
            when (authState) {
                StringConstants.userNotExists ->
                    findNavController().navigate(R.id.action_launchAppFragment_to_registerFragment)
                StringConstants.refreshSuccess ->
                    findNavController().navigate(R.id.action_launchAppFragment_to_profileFragment)
                StringConstants.tokenIsValid ->
                    findNavController().navigate(R.id.action_launchAppFragment_to_profileFragment)
                else -> findNavController().navigate(R.id.action_launchAppFragment_to_loginFragment)
            }
        }.launchWhenStarted(lifecycleScope)

        launchAppViewModel.authErrorResponseMessage.onEach { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }.launchWhenResumed(lifecycleScope)
    }
}