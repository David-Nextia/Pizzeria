package com.hybridge.camvvmpizza.domain.model

data class CartItem(
    val id: Long,
    val pizzaName: String,
    val unitPrice: Double,
    val quantity: Int
)
