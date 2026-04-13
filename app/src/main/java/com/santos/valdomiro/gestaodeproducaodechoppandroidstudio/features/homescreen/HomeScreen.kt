package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val navController = LocalNavController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = { navController.navigate(Route.ListaDeBarrisRoute.route) },
            nome = "Lista de barris"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = { navController.navigate(Route.ListaDeProdutosRoute.route) },
            nome = "Lista de produtos"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = { navController.navigate(Route.ListaDeGradesRoute.route) },
            nome = "Adicionar Grade"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = { navController.navigate(Route.ListaDeProducoesRoute.route) },
            nome = "Lista de porduções"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = { navController.navigate(Route.AdicionarQtHorariaRoute.route) },
            nome = "Adicionar Quantidade Horária"
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomeScreen()
}