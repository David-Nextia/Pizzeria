package com.hybridge.camvvmpizza.domain.usecase

import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.domain.repository.PizzaRepository

class GetAllPizzasUseCase(private val repository: PizzaRepository) {
    fun execute():List<Pizza> = repository.getAllPizzas()
}