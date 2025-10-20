package com.hybridge.camvvmpizza.data.repository

import com.hybridge.camvvmpizza.R
import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.domain.repository.PizzaRepository

class PizzaRepositoryImpl : PizzaRepository {
    private val pizzas = listOf(
        Pizza("Pepperoni", 180.0, R.drawable.pepperoni),
        Pizza("Hawaiana", 160.0, R.drawable.hawaiana),
        Pizza("Mexicana", 190.0, R.drawable.mexicana),
        Pizza("Pepperoni", 180.0, R.drawable.pepperoni),
        Pizza("Hawaiana", 160.0, R.drawable.hawaiana),
        Pizza("Mexicana", 190.0, R.drawable.mexicana),
        Pizza("Pepperoni", 180.0, R.drawable.pepperoni),
        Pizza("Hawaiana", 160.0, R.drawable.hawaiana),
        Pizza("Mexicana", 190.0, R.drawable.mexicana)
    )



    override fun getPizzaOfTheDay(): Pizza {
        return pizzas.random()
    }

    override fun getAllPizzas(): List<Pizza> = pizzas
}