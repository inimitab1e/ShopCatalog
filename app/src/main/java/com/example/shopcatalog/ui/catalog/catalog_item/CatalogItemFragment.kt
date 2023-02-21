package com.example.shopcatalog.ui.catalog.catalog_item

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentCatalogItemBinding
import com.example.shopcatalog.domain.utils.StringConstants
import com.example.shopcatalog.extensions.addOne
import com.example.shopcatalog.extensions.launchWhenStarted
import com.example.shopcatalog.extensions.removeOne
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CatalogItemFragment : Fragment(R.layout.fragment_catalog_item) {

    private val binding by viewBinding(FragmentCatalogItemBinding::bind)
    private val catalogItemViewModel: CatalogItemViewModel by viewModels()
    private val catalogShareDataViewModel: CatalogShareDataViewModel by activityViewModels()

    private var countValue = StringConstants.defaultCatalogItemCount
    private var itemName = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initClickers()
    }

    private fun initObservers() {
        catalogShareDataViewModel.catalogItemName.onEach { itemName ->
            binding.tvItemName.text = itemName
            catalogItemViewModel.getCatalogItemCount(itemName)
        }.launchWhenStarted(lifecycleScope)

        catalogItemViewModel.catalogItemCount.onEach { count ->
            setCountValue(count)
            setupRemoveButtonUiActivity()
        }.launchWhenStarted(lifecycleScope)
    }

    private fun setCountValue(count: String?) {
        if (count == null) {
            countValue = StringConstants.defaultCatalogItemCount
            binding.tvCountOfItems.text = countValue
        } else {
            countValue = count
            binding.tvCountOfItems.text = countValue
        }
    }

    private fun setupRemoveButtonUiActivity() {
        if (countValue == StringConstants.defaultCatalogItemCount) {
            binding.ibRemoveItemToCart.apply {
                isClickable = false
                setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
            }
        } else {
            binding.ibRemoveItemToCart.apply {
                isClickable = true
                colorFilter = null
            }
        }
    }

    private fun initClickers() {
        with(binding) {
            ibAddItemToCart.setOnClickListener {
                itemName = binding.tvItemName.text.toString()
                catalogItemViewModel.addItem(itemName, countValue.addOne())
            }

            ibRemoveItemToCart.setOnClickListener {
                itemName = binding.tvItemName.text.toString()
                catalogItemViewModel.removeItem(itemName, countValue.removeOne())
            }
        }
    }
}