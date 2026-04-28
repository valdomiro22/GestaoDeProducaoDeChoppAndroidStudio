package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.LinhaChaveValorInfo
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.buscarbarril.BuscarBarrilViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.StringUtils

@Composable
fun InfoProducaoDialog(
    producao: ProducaoEntity,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {},
    buscarBarrilViewModel: BuscarBarrilViewModel = hiltViewModel()
) {
    val barrilState by buscarBarrilViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        buscarBarrilViewModel.buscarBarril(producao.barrilId)
    }

    when {
        barrilState.isSuccess -> {
            val barril = (barrilState as? UiState.Success)?.data ?: run {
                ErroComponent("Barril não encontrado")
                return
            }

            val pendente = producao.quantidadeProgramada - producao.quantidadeProduzida
            val volumeNecessario = StringUtils.calcularVolumeNecessario(
                quantidade = pendente,
                vlBarril = barril.volume
            )

            AlertDialog(
                onDismissRequest = onDismiss,
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD44545))
                            .border(
                                width = 2.dp,
                                color = Color(0xFFD44545),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(vertical = 8.dp),

                        text = producao.produtoNome,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LinhaChaveValorInfo(chave = "Barril", valor = producao.barrilNome)
                        HorizontalDivider()

                        LinhaChaveValorInfo(chave = "Status", valor = producao.status.label)
                        HorizontalDivider()

                        LinhaChaveValorInfo(
                            chave = "Quantidade programada",
                            valor = producao.quantidadeProgramada.toString()
                        )
                        HorizontalDivider()

                        LinhaChaveValorInfo(
                            chave = "Quantidade produzida",
                            valor = producao.quantidadeProduzida.toString()
                        )
                        HorizontalDivider()

                        LinhaChaveValorInfo(
                            chave = "Quantidade pendente",
                            valor = pendente.toString()
                        )
                        HorizontalDivider()

                        LinhaChaveValorInfo(
                            chave = "Volume necessário",
                            valor = "$volumeNecessario hl"
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        onConfirm()
                        onDismiss()
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = { }
            )
        }
    }
}