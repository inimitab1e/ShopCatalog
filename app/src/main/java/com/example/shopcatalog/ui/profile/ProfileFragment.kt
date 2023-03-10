package com.example.shopcatalog.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.clear
import coil.load
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentProfileBinding
import com.example.shopcatalog.domain.utils.StringConstants
import com.example.shopcatalog.extensions.launchWhenStarted
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val profileViewModel: ProfileViewModel by viewModels()

    private var selectedGenderIndex = 0
    private val gendersToSetInDialog =
        arrayOf(StringConstants.genderMale, StringConstants.genderFemale)
    private var selectedGender = gendersToSetInDialog[selectedGenderIndex]

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { contentUri ->
            with(binding.ivProfileAvatar) {
                clear()
                load(contentUri) {
                    listener(
                        onError = { request, throwable ->
                            Toast.makeText(request.context, throwable.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                    listener(
                        onSuccess = { _, _ ->
                            profileViewModel.updateUserProfileImage(contentUri!!)
                        }
                    )
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStack();
            }
            activity?.finish()
        }
    }

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

            btnChangeProfilePhoneNumber.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_changePhoneNumberFragment)
            }

            btnChangeProfileGender.setOnClickListener {
                showDialog()
            }

            btnChangeProfileAvatar.setOnClickListener {
                pickImage.launch(MIMETYPE_IMAGES)
            }
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(StringConstants.genderDialogTitle)
            .setSingleChoiceItems(gendersToSetInDialog, selectedGenderIndex) { _, which ->
                selectedGender = gendersToSetInDialog[which]
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
                val path = userInfo.profileImage.path
                if (path != null) {
                    ivProfileAvatar.load(File(path))
                }
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun updateUserGenderInfo() {
        profileViewModel.updateUserGender(selectedGender)
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title =
            StringConstants.profileFragmentBarTitle
    }

    companion object {
        private const val MIMETYPE_IMAGES = "image/*"
    }
}