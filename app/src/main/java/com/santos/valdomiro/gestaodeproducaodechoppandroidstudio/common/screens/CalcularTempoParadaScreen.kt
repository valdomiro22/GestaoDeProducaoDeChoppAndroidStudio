package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.TimePickerDialog
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer.AppDrawer
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcularTempoParadaScreen(
    onOpenDrawer: () -> Unit,
    viewModel: CalcularTempoParadaViewModel = viewModel()
) {
    val inicio = viewModel.inicio
    val fim = viewModel.fim
    val diferenca = viewModel.diferenca

    var mostrarDialogInicio by remember { mutableStateOf(false) }
    var mostrarDialogFim by remember { mutableStateOf(false) }

    // CONFIGURAÇÃO DO DRAWER
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current

    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                onNavigate = { rotaClicada ->
                    scope.launch {
                        drawerState.close()

                        navController.navigate(rotaClicada.route) {
                            launchSingleTop = true
                        }
                    }
                },
                onCloseDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Calculadora de Horas") },
                    navigationIcon = {
                        IconButton(
                            onClick = { onOpenDrawer() }  // Abre o drawer
                        ) {
                            // Troquei o ArrowBack pelo ícone de Menu
                            Icon(Icons.Default.Menu, tint = Color.White, contentDescription = "Abrir Menu")
                        }
                    },
                    windowInsets = WindowInsets(0),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF6450A1),
                        titleContentColor = Color.White
                    ),
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botões de seleção de horário
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { mostrarDialogInicio = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // blueStrong
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = inicio?.let { "Início: ${formatarHora(it)}" }
                                ?: "Selecionar início"
                        )
                    }

                    Button(
                        onClick = { mostrarDialogFim = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = fim?.let { "Fim: ${formatarHora(it)}" }
                                ?: "Selecionar fim"
                        )
                    }
                }

                // Card com resultado
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF5D4037)), // brown
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (diferenca == null) {
                                "Selecione os horários"
                            } else {
                                val horas = diferenca.toHours()
                                // Pega o total de minutos e subtrai os minutos que já formam horas cheias
                                val minutos = diferenca.toMinutes() % 60
                                "Duração: ${horas}h ${minutos}m"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Dialog para Início
        if (mostrarDialogInicio) {
            TimePickerDialog(
                onDismiss = { mostrarDialogInicio = false },
                onConfirm = { selectedTime ->
                    viewModel.atualizarInicio(selectedTime)
                    mostrarDialogInicio = false
                },
                initialTime = inicio ?: LocalTime.now()
            )
        }

        // Dialog para Fim
        if (mostrarDialogFim) {
            TimePickerDialog(
                onDismiss = { mostrarDialogFim = false },
                onConfirm = { selectedTime ->
                    viewModel.atualizarFim(selectedTime)
                    mostrarDialogFim = false
                },
                initialTime = fim ?: LocalTime.now()
            )
        }
    }
}


// Função auxiliar para formatar hora (igual ao .format(context) do Flutter)
private fun formatarHora(time: LocalTime): String {
    return String.format("%02d:%02d", time.hour, time.minute)
}