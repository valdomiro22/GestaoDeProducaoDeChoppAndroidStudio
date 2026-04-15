package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.entity.BarrilEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG

@Composable
fun ItemListaProducao(
    producao: ProducaoEntity,
    onEditarClick: () -> Unit,
    onDeletarClick: () -> Unit,
    onDetalhesClick: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF8E72DB)
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = { navController.navigate(Route.HomeRoute.criarRota(producao.id!!)) }
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
            ) {
                Log.d(TAG, "ItemListaProducao: Produto: ${producao.produtoNome}")
                Text(
                    producao.produtoNome,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Barril de ${producao.barrilNome}",
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
            }

            Row {
                TextButton(onClick = onDetalhesClick) { Text("Detalhes", color = Color.White) }
                TextButton(onClick = onEditarClick) { Text("Editar", color = Color.White) }
                TextButton(onClick = onDeletarClick) { Text("Deletar", color = Color.White) }
            }
        }
    }
}
