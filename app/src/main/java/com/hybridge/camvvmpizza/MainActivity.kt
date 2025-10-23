package com.hybridge.camvvmpizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.ui.PizzaViewModel
import com.hybridge.camvvmpizza.ui.theme.PizzeriaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaTheme {
                PizzeriaApp()
            }
        }
    }
}

@Composable
fun PizzeriaApp() {
    val navController = rememberNavController()
    val viewModel: PizzaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable(route = "menu") {
            MenuScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = "detalle/{pizzaName}") { backStackEntry ->
            val pizzaName = backStackEntry.arguments?.getString("pizzaName")
            PizzaDetailScreen(
                pizzaName = pizzaName,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(route = "carrito") {
            CartScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: PizzaViewModel
) {
    val pizzas = viewModel.pizzaList
    val cartCount = viewModel.cartItems.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🍕 Menú de Pizzas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) {
                                    Badge { Text("$cartCount") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Ver carrito"
                            )
                        }
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            items(pizzas) { pizza ->
                PizzaItem(
                    pizza = pizza,
                    onClick = { navController.navigate("detalle/${pizza.type}") }
                )
            }
        }
    }
}

@Composable
fun PizzaItem(
    pizza: Pizza,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = pizza.imageRes),
                contentDescription = pizza.type,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = pizza.type,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Precio: $${pizza.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaDetailScreen(
    pizzaName: String?,
    viewModel: PizzaViewModel,
    navController: NavController
) {
    val pizza = viewModel.findPizzaByName(pizzaName)
    val cartCount = viewModel.cartItems.size
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de ${pizza?.type ?: pizzaName}") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) {
                                    Badge { Text("$cartCount") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Ir al carrito"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (pizza != null) {
                Image(
                    painter = painterResource(id = pizza.imageRes),
                    contentDescription = pizza.type,
                    modifier = Modifier.size(160.dp)
                )
                Text(text = "Precio: $${pizza.price}")
                Button(onClick = {
                    viewModel.addToCart(pizza)
                    scope.launch {
                        snackbarHostState.showSnackbar("Pizza agregada al carrito")
                    }
                }) {
                    Text("Agregar al carrito 🛒")
                }
            } else {
                Text("No se encontró información para '$pizzaName'")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: PizzaViewModel,
    navController: NavController
) {
    val cart = viewModel.cartItems
    val cartCount = cart.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🛒 Carrito ($cartCount)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    if (cart.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearCart() }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Vaciar carrito"
                            )
                        }
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (cart.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("El carrito está vacío 😢")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cart) { pizza ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = pizza.imageRes),
                                contentDescription = pizza.type,
                                modifier = Modifier.size(60.dp)
                            )
                            Column {
                                Text(
                                    text = pizza.type,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text("Precio: $${pizza.price}")
                            }
                        }
                    }
                }
            }
        }
    }
}
