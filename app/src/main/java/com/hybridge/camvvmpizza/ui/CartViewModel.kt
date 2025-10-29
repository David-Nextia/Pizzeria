package com.hybridge.camvvmpizza.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybridge.camvvmpizza.data.local.entity.CartItemEntity
import com.hybridge.camvvmpizza.data.repository.CartRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepositoryImpl) : ViewModel() {
    val cartItems: StateFlow<List<CartItemEntity>> = repository.observeCart().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val cartCount: StateFlow<Int> = cartItems
        .map { list -> list.sumOf { it.quantity } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun addPizza(name: String, price: Double) = viewModelScope.launch {
        repository.addPizza(name, price)
    }

    fun incrementQuantity(id: Long) = viewModelScope.launch {
        repository.incrementQuantity(id)
    }

    fun decrementQuantity(id: Long) = viewModelScope.launch {
        repository.decrementQuantity(id)
    }

    fun removeItem(id: Long) = viewModelScope.launch {
        repository.removeItem(id)
    }

    fun clearCart() = viewModelScope.launch { repository.clearCart() }
}