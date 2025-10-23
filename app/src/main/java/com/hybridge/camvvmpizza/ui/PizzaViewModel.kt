package com.hybridge.camvvmpizza.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hybridge.camvvmpizza.data.repository.PizzaRepositoryImpl
import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.domain.usecase.GetAllPizzasUseCase
import com.hybridge.camvvmpizza.domain.usecase.GetPizzaOfDayUseCase


class PizzaViewModel : ViewModel() {

    private val getPizzaUseCase = GetPizzaOfDayUseCase(PizzaRepositoryImpl())
    private val getAllPizzasUseCase = GetAllPizzasUseCase(PizzaRepositoryImpl())

    var pizzaOfDay by mutableStateOf(getPizzaUseCase.execute())
        private set
    var pizzaList by mutableStateOf(getAllPizzasUseCase.execute())
        private set
    var cartItems by mutableStateOf<List<Pizza>>(emptyList())
        private set

    fun refreshPizza() {
        pizzaOfDay = getPizzaUseCase.execute()
    }

    fun refreshPizzaList(){
        pizzaList = getAllPizzasUseCase.execute()
    }

    fun addToCart(pizza: Pizza) {
        cartItems = cartItems + pizza
    }

    fun clearCart() {
        cartItems = emptyList()
    }

    fun findPizzaByName(name: String?): Pizza? {
        if (name.isNullOrBlank()) return null
        return pizzaList.firstOrNull { it.type == name }
    }
}
