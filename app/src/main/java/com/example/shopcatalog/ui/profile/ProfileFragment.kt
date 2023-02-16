package com.example.shopcatalog.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentProfileBinding
import com.example.shopcatalog.domain.utils.StringConstants
import com.example.shopcatalog.extensions.launchWhenStarted
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val profileViewModel: ProfileViewModel by viewModels()

    private var selectedItemIndex = 0
    private val genders = arrayOf(StringConstants.genderMale, StringConstants.genderFemale)
    private var selectedItem = genders[selectedItemIndex]

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickers()
        currentUserInfoUiSetup()
    }

    private fun initClickers() {
        with(binding) {
            btnLogout.setOnClickListener {
                profileViewModel.doLogout()
                findNavController().navigate(R.id.action_profileFragment_to_registerFragment)
            }

            btnChangeProfileGender.setOnClickListener {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(StringConstants.genderDialogTitle)
            .setSingleChoiceItems(genders, selectedItemIndex) { _, which ->
                selectedItem = genders[which]
            }
            .setPositiveButton(StringConstants.genderDialogSaveButton) { _, _ ->
                updateUserGenderInfo()
            }
            .setNegativeButton(StringConstants.genderDialogCloseButton) { _, _ ->
            }
            .show()
    }

    private fun currentUserInfoUiSetup() {
        profileViewModel.currentLocalUserInfo.onEach { userInfo ->
            with(binding) {
                tvProfileName.text = userInfo.userName
                tvProfileEmail.text = userInfo.email
                tvProfileGender.text = userInfo.gender
                tvProfilePhoneNumber.text = userInfo.phoneNumber
                //TODO profile image
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun updateUserGenderInfo() {
        binding.tvProfileGender.text = selectedItem
    }
}