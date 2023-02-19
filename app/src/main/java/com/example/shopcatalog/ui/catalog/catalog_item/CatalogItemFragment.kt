package com.example.shopcatalog.ui.catalog.catalog_item

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentCatalogItemBinding
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CatalogItemFragment : Fragment(R.layout.fragment_catalog_item) {

    private val binding by viewBinding(FragmentCatalogItemBinding::bind)
    private val catalogItemViewModel: CatalogItemViewModel by viewModels()
    private val catalogShareDataViewModel: CatalogShareDataViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catalogShareDataViewModel.catalogItemName.onEach { itemName ->
            binding.tvItemName.text = itemName
        }.launchWhenStarted(lifecycleScope)
    }
}