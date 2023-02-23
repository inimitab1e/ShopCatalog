package com.example.shopcatalog.ui.catalog.catalog_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.repository.CatalogRepository
import com.example.shopcatalog.domain.utils.StringConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogItemViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _catalogItemCount =
        MutableStateFlow<String?>(StringConstants.defaultCatalogItemCount)
    val catalogItemCount: StateFlow<String?> get() = _catalogItemCount.asStateFlow()

    fun getCatalogItemCount(catalogItemName: String) {
        viewModelScope.launch {
            catalogRepository.getExistingCatalogItems(catalogItemName)
                .collect { catalogItemInCart ->
                    _catalogItemCount.value = catalogItemInCart?.catalogItemCount
                }
        }
    }

    fun addItem(itemName: String, count: String) {
        viewModelScope.launch {
            catalogRepository.addOneItemToCart(itemName, count)
        }
    }

    fun removeItem(itemName: String, count: String) {
        viewModelScope.launch {
            catalogRepository.deleteOneItemFromCart(itemName, count)
        }
    }
}