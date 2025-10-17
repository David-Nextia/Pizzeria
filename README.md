# CAMVVMPIZZA — Curso Android (hasta Clase 5)

> **Estado del repo en `main`:** Estructura base del proyecto Android con **Jetpack Compose + MVVM + Clean Architecture (mínimo viable)** para las clases **1 a la 5**. **No se tocan** temas de navegación ni pantallas múltiples aún.

## ¿Qué hay implementado?
- **Capa de dominio:**
  - `Pizza` (modelo)
  - `PizzaRepository` (contrato)
  - Casos de uso: `GetPizzaOfDayUseCase`, `GetAllPizzasUseCase`
- **Capa de datos:**
  - `PizzaRepositoryImpl` con lista de pizzas mock (e imágenes en `res/drawable/`)
- **Capa de UI (Compose):**
  - `PizzaViewModel` (MVVM)
  - `MainActivity` con `PizzaMenuScreen` que lista pizzas y una tarjeta (`PizzaCard`)
  - **Tema Material 3** (colores y tipografía)

## Versionado / Tooling
- Gradle 8.13, Kotlin 2.0.21, Compose BOM 2024.09.00
- `minSdk` 24, `targetSdk` 36

## Próximos pasos (a partir de la Clase 6, **no incluidos en `main`**):
- Navegación con **Jetpack Navigation Compose**
- Pantallas múltiples y paso de argumentos
- Estados avanzados, ViewModel + SavedStateHandle

---

### Notas para alumnos
- Este repo refleja exactamente **lo visto hasta la Clase 5**.
- Para nuevas funcionalidades, se crearán **ramas** y **PRs** sin alterar `main` hasta que se revisen en clase.

---

_Mantén Android Studio actualizado y configura el JDK 21 (jbr-21) como sugiere el proyecto._
