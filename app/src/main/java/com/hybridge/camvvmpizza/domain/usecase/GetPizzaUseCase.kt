package com.hybridge.camvvmpizza.domain.usecase

import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.domain.repository.PizzaRepository

class GetPizzaOfDayUseCase(private val repository: PizzaRepository) {
    fun execute(): Pizza {
        val pizza = repository.getPizzaOfTheDay()
        val discounted = pizza.price * 0.9
        return pizza.copy(price = discounted)
    }
}