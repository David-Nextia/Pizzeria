package com.hybridge.camvvmpizza.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hybridge.camvvmpizza.data.repository.PizzaRepositoryImpl
import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.domain.usecase.GetPizzaOfDayUseCase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hybridge.camvvmpizza.domain.usecase.GetAllPizzasUseCase

// PizzaViewModel.kt
class PizzaViewModel : ViewModel() {

    private val getPizzaUseCase = GetPizzaOfDayUseCase(PizzaRepositoryImpl())
    private val getAllPizzasUseCase = GetAllPizzasUseCase(PizzaRepositoryImpl())

    // Pizza del día
    var pizzaOfDay by mutableStateOf(getPizzaUseCase.execute())
        private set

    // Lista completa de pizzas
    var pizzaList by mutableStateOf(getAllPizzasUseCase.execute())
        private set

    fun refreshPizzaOfDay() {
        pizzaOfDay = getPizzaUseCase.execute()
    }

    fun refreshPizzaList() {
        pizzaList = getAllPizzasUseCase.execute()
    }
}
