package com.example.shopcatalog.domain.repository

import com.example.shopcatalog.domain.model.CatalogItemsList

interface CatalogRepository {
    suspend fun getListOfCatalogItems(): CatalogItemsList
}