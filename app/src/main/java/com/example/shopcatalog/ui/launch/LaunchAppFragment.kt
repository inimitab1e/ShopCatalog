package com.example.shopcatalog.ui.launch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentLaunchAppBinding
import com.example.shopcatalog.domain.utils.StringConstants
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LaunchAppFragment : Fragment(R.layout.fragment_launch_app) {

    private val binding by viewBinding(FragmentLaunchAppBinding::bind)
    private val launchAppViewModel: LaunchAppViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAppViewModel.isUserExist.onEach { isUserExist ->
            if (!isUserExist) {
                findNavController().navigate(R.id.action_launchAppFragment_to_registerFragment)
            }
        }.launchWhenStarted(lifecycleScope)

        launchAppViewModel.isAccessTokenValid.onEach { isAccessTokenValid ->
            if (isAccessTokenValid) {
                findNavController().navigate(R.id.action_launchAppFragment_to_profileFragment)
            } else {
                launchAppViewModel.tryToRefreshUserTokens()
            }
        }.launchWhenStarted(lifecycleScope)

        launchAppViewModel.isRefreshSuccess.onEach { isRefreshSuccess ->
            if (isRefreshSuccess) {
                findNavController().navigate(R.id.action_launchAppFragment_to_profileFragment)
            } else {
                findNavController().navigate(R.id.action_launchAppFragment_to_loginFragment)
            }
        }.launchWhenStarted(lifecycleScope)

        launchAppViewModel.errorAccessValidityResponseMessage.onEach { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }.launchWhenStarted(lifecycleScope)

        launchAppViewModel.errorRefreshResponseMessage.onEach { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }.launchWhenStarted(lifecycleScope)
    }
}