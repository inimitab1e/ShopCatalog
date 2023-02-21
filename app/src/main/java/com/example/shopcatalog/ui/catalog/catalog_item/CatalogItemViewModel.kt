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
        MutableSharedFlow<String?>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val catalogItemCount: SharedFlow<String?> get() = _catalogItemCount.asSharedFlow()

    fun getCatalogItemCount(catalogItemName: String) {
        viewModelScope.launch {
            catalogRepository.getExistingCatalogItems(catalogItemName)
                .collect { catalogItemInCart ->
                    _catalogItemCount.tryEmit(catalogItemInCart?.catalogItemCount)
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