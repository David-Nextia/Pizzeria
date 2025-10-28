package com.hybridge.camvvmpizza.data.repository

import com.hybridge.camvvmpizza.data.local.dao.CartDao
import com.hybridge.camvvmpizza.data.local.entity.CartItemEntity
import com.hybridge.camvvmpizza.domain.model.CartItem
import com.hybridge.camvvmpizza.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(private val dao: CartDao) : CartRepository {

    override fun observeCart(): Flow<List<CartItem>> =
        dao.observeCart().map { entities -> entities.map { it.toDomain() } }

    override suspend fun addPizza(name: String, price: Double) {
        val existing = dao.findByName(name)
        val updated = (existing ?: CartItemEntity(pizzaName = name, unitPrice = price, quantity = 0))
            .copy(unitPrice = price, quantity = (existing?.quantity ?: 0) + 1)
        dao.upsert(updated)
    }

    override suspend fun incrementQuantity(id: Long) {
        val entity = dao.findById(id) ?: return
        dao.upsert(entity.copy(quantity = entity.quantity + 1))
    }

    override suspend fun decrementQuantity(id: Long) {
        val entity = dao.findById(id) ?: return
        val newQuantity = entity.quantity - 1
        if (newQuantity > 0) {
            dao.upsert(entity.copy(quantity = newQuantity))
        } else {
            dao.delete(entity)
        }
    }

    override suspend fun removeItem(id: Long) {
        dao.findById(id)?.let { dao.delete(it) }
    }

    override suspend fun clearCart() {
        dao.clear()
    }

    private fun CartItemEntity.toDomain(): CartItem =
        CartItem(
            id = id,
            pizzaName = pizzaName,
            unitPrice = unitPrice,
            quantity = quantity
        )
}
