# 🍕 Pizzeria App – Curso de Desarrollo Móvil con Kotlin y Jetpack Compose

Este repositorio contiene el código **hasta la Clase 5** del curso.

## 📚 Estado actual: Clase 5 — Principios de diseño móvil con Material (Tema, Colores y Tipografía)

### 🔹 Contenido cubierto
- Arquitectura **MVVM + Clean Architecture** aplicada a la app de la pizzería.
- Implementación del **tema visual** con **Material Design 3**.
- Configuración de **colores**, **tipografía** y **modo claro/oscuro**.
- Lista visual de pizzas implementada en **Jetpack Compose**.
- Separación en capas:
  - **Domain:** entidades y casos de uso.
  - **Data:** repositorios y fuentes de datos.
  - **Presentation (UI):** ViewModel y pantallas Compose.

### 📦 Estructura del proyecto
```
app/
 └── src/main/java/com/example/pizzeria/
      ├── data/
      │    └── repository/PizzaRepositoryImpl.kt
      ├── domain/
      │    ├── model/Pizza.kt
      │    ├── repository/PizzaRepository.kt
      │    └── usecase/GetPizzaOfDayUseCase.kt
      ├── ui/
      │    ├── PizzaViewModel.kt
      │    └── PizzaMenuScreen.kt
      └── PizzeriaTheme.kt
```

### 🧠 Objetivo de esta etapa
Consolidar la **identidad visual** de la aplicación usando los principios de Material Design 3:
- Aplicar temas personalizados.
- Unificar colores y tipografías.
- Mantener una arquitectura limpia y modular.

### ⚠️ Nota
> Este código es el **punto final de la Clase 5**.  
> No contiene todavía la parte de **navegación ni pantallas múltiples**, la cual se implementará a partir de la **Clase 6**.
