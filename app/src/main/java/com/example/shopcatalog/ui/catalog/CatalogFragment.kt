package com.example.shopcatalog.ui.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentCatalogBinding
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val binding by viewBinding(FragmentCatalogBinding::bind)
    private val catalogViewModel: CatalogViewModel by viewModels()
    private val catalogRwAdapter: CatalogAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CatalogAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rwCtalogtList.apply {
            layoutManager =
                GridLayoutManager(context, countOfColumnsInCatalogRw, RecyclerView.VERTICAL, false)
            adapter = catalogRwAdapter
        }

        catalogViewModel.catalogItemsList.onEach { catalogItemsList ->
            catalogRwAdapter.setCatalogItemsList(catalogItemsList.catalogList)
        }.launchWhenStarted(lifecycleScope)
    }

    companion object {
        private const val countOfColumnsInCatalogRw = 3
    }
}