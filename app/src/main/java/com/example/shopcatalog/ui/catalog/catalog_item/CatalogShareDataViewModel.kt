package com.example.shopcatalog.ui.catalog.catalog_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.utils.StringConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogShareDataViewModel @Inject constructor(

) : ViewModel() {

    private val _catalogItemName = MutableStateFlow(StringConstants.unknownCatalogItemName)
    val catalogItemName: StateFlow<String> get() = _catalogItemName.asStateFlow()

    fun setItemNameToShare(itemName: String) {
        viewModelScope.launch {
            _catalogItemName.emit(itemName)
        }
    }
}