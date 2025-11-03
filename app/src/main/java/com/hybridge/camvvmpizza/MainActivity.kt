package com.hybridge.camvvmpizza
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hybridge.camvvmpizza.data.local.AppDatabase
import com.hybridge.camvvmpizza.data.repository.CartRepositoryImpl
import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.ui.CartViewModel
import com.hybridge.camvvmpizza.ui.PizzaViewModel
import com.hybridge.camvvmpizza.ui.theme.PizzeriaTheme


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
    val context = LocalContext.current // NUEVO
    val navController = rememberNavController()
    val pizzaViewModel: PizzaViewModel = viewModel()
    val database = remember { AppDatabase.getInstance(context) } // NUEVO
    val repository = remember { CartRepositoryImpl(database.cartDao()) } // NUEVO
    val cartViewModel: CartViewModel = viewModel( // NUEVO
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(repository) as T
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable(route = "menu") {
            MenuScreen(navController, pizzaViewModel, cartViewModel)
        }
        composable(route = "detalle/{pizzaName}") { backStackEntry ->
            val pizzaName = backStackEntry.arguments?.getString("pizzaName")
           // PizzaDetailScreen(pizzaName, pizzaViewModel, cartViewModel, navController)
        }

        composable("carrito"){
           // CartScreen(pizzaViewModel, cartViewModel, navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, viewModel: PizzaViewModel = viewModel(), cartViewModel: CartViewModel ) {
    val pizzas = viewModel.pizzaList
    val cartCount by cartViewModel.cartCount.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🍕 Menú de Pizzas", fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ), actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) { // MODIFICADO
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) { // MODIFICADO
                                    Badge { Text("$cartCount") } // MODIFICADO
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pizzas) { pizza ->

                PizzaItem(pizza = pizza) {
                    navController.navigate("detalle/${pizza.type}")
                    println("se hizo click ${ pizza.type}")
                }
            }
        }




    }

}


@Composable
fun PizzaItem(pizza: Pizza, onClick: () -> Unit){
    Card (colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),

        modifier = Modifier.fillMaxWidth().clickable{
            onClick()
        }){

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
    viewModel: PizzaViewModel = viewModel(),
    cartViewModel: CartViewModel,
    navController: NavController
) {
    val pizza = remember(pizzaName){ viewModel.findPizzaByName(pizzaName)}
    val cartCount by cartViewModel.cartCount.collectAsState()
   
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()



    Scaffold(
        topBar = { TopAppBar(title = {
            Text("Detalle de ${pizzaName}")
        }, actions = {
            IconButton(onClick = { navController.navigate("carrito") }) {
                BadgedBox(badge = {
                    if (cartCount> 0){
                        Badge { Text("$cartCount") }
                    }
                } ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Ir al carrito")
                }
            }
        })
        }
    ) {  padding ->
       Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
           if(pizza != null){

               Text("Tipo: ${pizza.type}", style = MaterialTheme.typography.titleLarge)
               Spacer(Modifier.height(8.dp))
               Text("Precio: $${pizza.price}")
               Spacer(Modifier.height(8.dp))
               Image(
                   painter = painterResource(id = pizza.imageRes),
                   contentDescription = pizza.type,
                   modifier = Modifier.size(160.dp)
               )



           }else
           { Text("No se encontró información para '$pizzaName'")}
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
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (cart.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearCart() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Vaciar")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (cart.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
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
                        elevation = CardDefaults.cardElevation(4.dp)
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
                                Text(pizza.type, style = MaterialTheme.typography.titleMedium)
                                Text("Precio: $${pizza.price}")
                            }
                        }
                    }
                }
            }
        }
    }
}