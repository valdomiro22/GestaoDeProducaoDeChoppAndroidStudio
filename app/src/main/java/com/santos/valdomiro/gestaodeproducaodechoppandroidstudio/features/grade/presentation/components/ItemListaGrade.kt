package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.core.utils.DataHoraUtils
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG
import java.time.LocalDate
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ItemListaGrade(
    grade: GradeEntity,
    onDetalheClick: () -> Unit,
    onEditarClick: () -> Unit,
    onDeletarClick: () -> Unit,
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
        onClick = { navController.navigate(Route.ListaDeProducoesRoute.criarRota(grade.id!!)) }
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
                Row {
//                    Text("Data: ", color = Color.White)
                    Text(
                        DataHoraUtils.formatarData(grade.data.toString()) ?: "N/A",
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    Text("Grade: ", color = Color.White)
                    Text(
                        grade.numero.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    )

                }
                Spacer(modifier = Modifier.height(6.dp))

            }

            Row {
                TextButton(onClick = onDetalheClick) { Text("Detalhes", color = Color.White) }
                TextButton(onClick = onEditarClick) { Text("Editar", color = Color.White) }
                TextButton(onClick = onDeletarClick) { Text("Deletar", color = Color.White) }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val grade = GradeEntity(
        id = "1",
        numero = 4,
        data = LocalDate.now(),
        quantidadeBarris = 392,
        volumeHlNecessario = 150.0,
    )

    ItemListaGrade(
        grade = grade,
        onEditarClick = { Log.d(TAG, "Editar clicado") },
        onDetalheClick = { Log.d(TAG, "Editar clicado") },
        onDeletarClick = { Log.d(TAG, "Deletar clicado") },
        navController = NavHostController(context = LocalContext.current)
    )
}