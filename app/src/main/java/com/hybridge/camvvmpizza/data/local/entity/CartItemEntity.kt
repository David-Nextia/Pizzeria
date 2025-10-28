package com.hybridge.camvvmpizza.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val pizzaName: String,
    val unitPrice: Double,
    val quantity: Int
)
