package com.hybridge.camvvmpizza.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hybridge.camvvmpizza.data.repository.PizzaRepositoryImpl
import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.domain.usecase.GetPizzaOfDayUseCase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue



class PizzaViewModel : ViewModel() {

    private val getPizzaUseCase = GetPizzaOfDayUseCase(PizzaRepositoryImpl())

    // mutableStateOf con delegación 'by'
    var pizzaState by mutableStateOf(getPizzaUseCase.execute())
        private set

    fun refreshPizza() {
        pizzaState = getPizzaUseCase.execute()
    }
}
