package com.example.shopcatalog.domain.repository

import com.example.shopcatalog.domain.model.CatalogItemInCart
import com.example.shopcatalog.domain.model.CatalogItemsList
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    suspend fun getListOfCatalogItems(): CatalogItemsList

    fun getCartSize(): Flow<Int>

    fun getExistingCatalogItems(catalogItemName: String): Flow<CatalogItemInCart?>

    fun getListOfCartItems(): Flow<List<CatalogItemInCart>>

    suspend fun addOneItemToCart(cartItemName: String, count: String)

    suspend fun deleteOrDecreaseOneItemFromCart(cartItemName: String, count: String)

    suspend fun deleteItemFromCart(cartItemName: String)

    suspend fun clearAllCartInfo()
}