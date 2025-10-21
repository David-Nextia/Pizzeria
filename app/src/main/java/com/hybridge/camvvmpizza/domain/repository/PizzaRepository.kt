package com.hybridge.camvvmpizza.domain.repository

import com.hybridge.camvvmpizza.domain.model.Pizza

interface PizzaRepository{
    fun getPizzaOfTheDay(): Pizza
    fun getAllPizzas():List<Pizza>

}