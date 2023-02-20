package com.example.shopcatalog.domain.repository

import com.example.shopcatalog.domain.model.CatalogItemInCart
import com.example.shopcatalog.domain.model.CatalogItemsList

interface CatalogRepository {
    suspend fun getListOfCatalogItems(): CatalogItemsList

    suspend fun getExistingCatalogItems(catalogItemName: String): CatalogItemInCart?

    suspend fun addOneItemToCart(catalogItemName: String, count: String)

    suspend fun deleteOneItemFromCart(catalogItemName: String, count: String)

    suspend fun clearAllCartInfo()
}