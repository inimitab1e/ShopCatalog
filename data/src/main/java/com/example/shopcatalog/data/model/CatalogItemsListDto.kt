package com.example.shopcatalog.data.model

import com.google.gson.annotations.SerializedName

data class CatalogItemsListDto(
    @SerializedName("CatalogList")
    val catalogList: List<String>
)
