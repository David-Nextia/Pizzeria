package com.hybridge.camvvmpizza
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hybridge.camvvmpizza.domain.model.Pizza
import com.hybridge.camvvmpizza.ui.PizzaViewModel
import com.hybridge.camvvmpizza.ui.theme.PizzeriaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaTheme {

                 PizzaMenuScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun PizzaScreen(viewModel: PizzaViewModel = viewModel()) {
    val pizza = viewModel.pizzaState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1))
            .padding(WindowInsets.safeDrawing.asPaddingValues()) // Evita el notch y bordes cortados
            .padding(16.dp), // Padding adicional interno
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "🍕 Pizza del día",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD84315),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().clickable{
            viewModel.refreshPizza()
        }

            // Acción al hacer click en la card


        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(pizza.type, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Precio: $${pizza.price}", fontSize = 18.sp, color = Color.Gray)
                }

                Image(
                    painter = painterResource(id = pizza.imageRes),
                    contentDescription = pizza.type,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

       ////////
        Button(onClick = { viewModel.refreshPizza() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Cambiar pizza del día")
        }
    }
}


@Composable
fun PizzaMenuScreen(viewModel: PizzaViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(WindowInsets.safeDrawing.asPaddingValues()) // Evita el notch y bordes cortados
            .padding(16.dp), // Padding adicional interno
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "🍕 Pizza del día",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD84315),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )



}
}