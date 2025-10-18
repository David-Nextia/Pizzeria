# CAMVVMPIZZA — Curso Android (Clase 6)

> **Estado del repo:** `main` conserva el proyecto base de las clases 1 a 5. La rama `clase-6` suma navegación con Jetpack Compose para trabajar pantallas múltiples.

## Ramas y alcance
- `main`: App mínima con **Jetpack Compose + MVVM + Clean Architecture**, listado de pizzas y tema Material 3. Ideal para arrancar o repasar las clases 1-5.
- `clase-6`: Extiende la base anterior con navegación declarativa. Implementa un flujo **Menú → Detalle** usando `NavController` y mantiene el estado en el `PizzaViewModel`.

## ¿Qué incorpora la Clase 6?
- `PizzeriaApp` define el grafo de navegación con `NavHost`, `rememberNavController()` y rutas `menu` / `detalle/{pizzaName}`.
- `MenuScreen` navega al detalle con `navController.navigate("detalle/${pizza.type}")`, elevando el estado al `ViewModel`.
- `PizzaItem` vuelve clickeables las tarjetas utilizando `Modifier.clickable`.
- `PizzaDetailScreen` recibe el parámetro de navegación, recupera la pizza desde el `ViewModel` y usa `remember(pizzaName)` para evitar recomposiciones innecesarias.
- Se refuerza la práctica de pasar **identificadores simples** por la ruta y reconstruir objetos en el `ViewModel`.

## Dependencias nuevas en `clase-6`
```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
```
- `navigation-compose` aporta `NavController`, `NavHost` y `composable`.
- `lifecycle-viewmodel-compose` permite obtener un `ViewModel` dentro de composables (`viewModel()`).

## Errores frecuentes a vigilar
- Pasar objetos completos por la ruta → usa solo IDs/nombres y consulta el `ViewModel`.
- Crear múltiples `PizzaViewModel` cuando comparten datos → define el `ViewModel` a nivel raíz o en el destino contenedor.
- Olvidar `rememberNavController()` → el controlador se recrea y pierde estado.
- No manejar argumentos nulos → valida `pizzaName` antes de usarlo para evitar crashes.

## Cómo seguir la clase
1. Cambia a la rama `clase-6` (`git checkout clase-6`) para acceder al código con navegación.
2. Revisa el archivo `📚6. Navegación y pantallas múltiples en Jetpack Compose.md` para ver ejemplos completos y teoría.
3. Ejecuta la app desde `PizzeriaApp` como raíz de Compose (en la rama `clase-6`).
4. Experimenta creando más destinos y pasando otros argumentos manteniendo el patrón MVVM.

## Versionado / Tooling
- Gradle 8.13, Kotlin 2.0.21, Compose BOM 2024.09.00
- `minSdk` 24, `targetSdk` 36
- JDK recomendado: 21 (jbr-21)

## Próximos pasos (Clase 7+)
- ViewModel + `SavedStateHandle` para restaurar estado en navegación compleja.
- Estados avanzados y manejo de back stack.
- Integración con Hilt para inyección de dependencias y `NavGraph`.

---

- Mantén Android Studio actualizado e importa el proyecto con el **Android Gradle Plugin** sugerido por el repositorio.
- El material de cada clase vive en ramas independientes para no alterar `main` hasta su repaso en vivo.
