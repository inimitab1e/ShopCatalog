package com.example.shopcatalog.ui.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val binding by viewBinding(FragmentCatalogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}