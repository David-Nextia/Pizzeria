package com.hybridge.camvvmpizza.domain.repository

import com.hybridge.camvvmpizza.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun addPizza(name: String, price: Double)
    suspend fun incrementQuantity(id: Long)
    suspend fun decrementQuantity(id: Long)
    suspend fun removeItem(id: Long)
    suspend fun clearCart()
}
