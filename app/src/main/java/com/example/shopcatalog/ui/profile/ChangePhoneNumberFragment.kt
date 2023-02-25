package com.example.shopcatalog.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentChangePhoneNumberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePhoneNumberFragment : Fragment(R.layout.fragment_change_phone_number) {

    private val binding by viewBinding(FragmentChangePhoneNumberBinding::bind)
    private val changePhoneNumberViewModel: ChangePhoneNumberViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickers()
    }

    private fun initClickers() {
        binding.btnChangePhoneNumber.setOnClickListener {
            changePhoneNumberViewModel.updateUserPhoneNumber(binding.etChangePhoneNumber.text.toString())
            findNavController().navigate(R.id.action_changePhoneNumberFragment_to_profileFragment)
        }
    }
}