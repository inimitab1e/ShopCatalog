package com.example.shopcatalog.ui.launch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentLaunchAppBinding

class LaunchAppFragment : Fragment(R.layout.fragment_launch_app) {

    private val binding by viewBinding(FragmentLaunchAppBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}