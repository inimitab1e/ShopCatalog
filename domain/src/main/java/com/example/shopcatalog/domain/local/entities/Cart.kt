package com.example.shopcatalog.domain.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true) var ID: Int? = null,
    var listOfProducts: Map<Int, Int>?
)
