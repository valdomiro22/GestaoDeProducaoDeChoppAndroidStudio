package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.components

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
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.entity.BarrilEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG

@Composable
fun ItemListaProduto(
    produto: ProdutoEntity,
    onEditarClick: () -> Unit,
    onDeletarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF8E72DB)
        ),
        shape = RoundedCornerShape(8.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .padding(10.dp)
                .weight(1f)) {
                Row {
                    Text("Nome: ", color = Color.White)
                    Text(
                        produto.nome,
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    Text("Validade: ", color = Color.White)
                    Text(
                        "${produto.prazoValidade} dias",
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    )

                }
                Spacer(modifier = Modifier.height(6.dp))

            }

            Row {
                TextButton(onClick = onEditarClick) { Text("Editar", color = Color.White) }
                TextButton(onClick = onDeletarClick) { Text("Deletar", color = Color.White) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val produto = ProdutoEntity(
        id = "asdo2432",
        nome = "Petra",
        prazoValidade = 30
    )
    ItemListaProduto(produto = produto, onEditarClick = {}, onDeletarClick = {})
}