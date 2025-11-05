package com.hybridge.camvvmpizza.data.repository

import com.hybridge.camvvmpizza.data.local.dao.CartDao
import com.hybridge.camvvmpizza.data.local.entity.CartItemEntity

class CartRepositoryImpl(private val dao: CartDao) {
    fun observeCart() = dao.observeCart()


    suspend fun addPizza(name: String, price: Double) {
        dao.upsert(CartItemEntity(pizzaName = name, unitPrice = price, quantity = 1))
    }
    suspend fun incrementQuantity(id: Long) {
        dao.findById(id)?.let { dao.upsert(it.copy(quantity = it.quantity + 1)) }
    }
    suspend fun decrementQuantity(id: Long) {
        dao.findById(id)?.let { entity ->
            val updated = entity.quantity - 1
            if (updated > 0) dao.upsert(entity.copy(quantity = updated)) else dao.delete(entity)
        }
    }
    suspend fun removeItem(id: Long) {
        dao.findById(id)?.let { dao.delete(it) }
    }
    suspend fun clearCart() = dao.clear()
}