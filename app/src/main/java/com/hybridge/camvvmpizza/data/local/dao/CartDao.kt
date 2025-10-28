package com.hybridge.camvvmpizza.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.hybridge.camvvmpizza.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items ORDER BY id DESC")
    fun observeCart(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE id = :id")
    suspend fun findById(id: Long): CartItemEntity?

    @Query("SELECT * FROM cart_items WHERE pizzaName = :pizzaName LIMIT 1")
    suspend fun findByName(pizzaName: String): CartItemEntity?

    @Upsert
    suspend fun upsert(item: CartItemEntity)

    @Delete
    suspend fun delete(item: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}
