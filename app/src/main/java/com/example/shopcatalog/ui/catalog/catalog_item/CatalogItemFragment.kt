package com.example.shopcatalog.ui.catalog.catalog_item

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentCatalogItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogItemFragment : Fragment(R.layout.fragment_catalog_item) {

    private val binding by viewBinding(FragmentCatalogItemBinding::bind)
    private val catalogItemViewModel: CatalogItemViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}