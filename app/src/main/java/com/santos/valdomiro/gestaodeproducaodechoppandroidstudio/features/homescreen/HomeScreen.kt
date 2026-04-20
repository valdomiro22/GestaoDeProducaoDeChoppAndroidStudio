package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer.AppDrawer
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer.DrawerViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.buscarproducao.BuscarProducaoViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.Turno
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    producaoId: String,
    viewModel: HomeViewModel = hiltViewModel(),
    buscarProducaoViewModel: BuscarProducaoViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val state by buscarProducaoViewModel.uiState.collectAsState()
    val turnoAtual by viewModel.turnoSelecionado.collectAsState()
    var menuExpandido by remember { mutableStateOf(false) }  // Para o controle do DropdownMenu
    val drawerViewModel: DrawerViewModel = hiltViewModel()
    val drawerStateLogout by drawerViewModel.uiState.collectAsState()

    // CONFIGURAÇÃO DO DRAWER
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        buscarProducaoViewModel.buscarProducao(producaoId)
    }

    LaunchedEffect(drawerStateLogout.isSuccess) {
        if (drawerStateLogout.isSuccess) {
            navController.navigate(Route.LoginRoute.route) {
                // ISSO É CRÍTICO: Limpa todo o histórico de telas
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Este é o conteúdo que aparece quando o menu lateral abre
            ModalDrawerSheet {
                AppDrawer(
                    selectedRoute = Route.HomeRoute.route, // Rota atual
                    onItemClick = { rotaClicada ->
                        scope.launch {
                            drawerState.close() // Fecha primeiro
                            navController.navigate(rotaClicada.route) {
                                // 1. Limpa a pilha de navegação até a tela inicial do app
                                // Isso evita que o "Back" fique voltando pelas telas do Drawer
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }

                                // 2. Evita abrir a mesma tela várias vezes se você clicar no menu repetidamente
                                launchSingleTop = true
                            }
                        }
                    },
                    onLogoutClick = { drawerViewModel.deslogar() }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Itaipava 50L",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },

                    // Botão para abrir o Drawer
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            // Troquei o ArrowBack pelo ícone de Menu
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menu")
                        }
                    },


                    actions = {
                        IconButton(onClick = { menuExpandido = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Mais opções",
                                tint = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpandido,
                            onDismissRequest = { menuExpandido = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Opção 1") },
                                onClick = {
                                    menuExpandido = false
                                    Toast.makeText(context, "Opção 1", Toast.LENGTH_SHORT).show()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Opção 2") },
                                onClick = {
                                    menuExpandido = false
                                    Toast.makeText(context, "Opção 2", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },

                    windowInsets = WindowInsets(0),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1E5FDB),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            when {
                state.isSuccess -> {
                    val producao = (state as? UiState.Success)?.data ?: run {
                        ErroComponent("Produção não encontrada")
                        return@Scaffold
                    }

                    val pendente = producao.quantidadeProgramada - producao.quantidadeProduzida

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Seção de status da produção
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CardProducaoComponent(
                                titulo = "Programado",
                                backGround = Color(0xFF1E5FDB),
                                quantidade = "${producao.quantidadeProgramada}"
                            )
                            CardProducaoComponent(
                                backGround = Color(0xFF15AD1C),
                                titulo = "Produzido",
                                quantidade = "${producao.quantidadeProduzida}"
                            )
                            CardProducaoComponent(
                                backGround = Color(0xFFE52828),
                                titulo = "Pendente",
                                quantidade = pendente.toString()
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // Seção de seleção de turno
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Turno.entries.forEach { turno ->
                                val selecionado = turno == turnoAtual
                                val corBase =
                                    if (selecionado) Color(0xFF2563EB) else Color(0xFFF0F0F0)

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(corBase)
                                        .clickable { viewModel.alterarTurno(turno) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = turno.label,
                                        color = if (selecionado) Color.White else Color.DarkGray,
                                        fontWeight = if (selecionado) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        // Seção de horários do turno selecionado
                        val listaDeHorarios = turnoAtual.horarios.values.toList()
                        QuantidadeHoraria(
                            horarios = listaDeHorarios, 
                            onClick = {
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Informações para o final de produção
                        FimDeProducao(
                            onClick = {
                                navController.navigate(
                                    Route.SimularFimProducaoRoute.criarRota(id = producaoId)
                                )
                            }
                        )
                    }
                }

                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 16.dp), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.isError -> {
                    ErroComponent(
                        mensagem = (state as? UiState.Error)?.message
                            ?: "Erro desconhecido ao buscar produção"
                    )
                }
            }
        }
    }
}

@Composable
fun QuantidadeHoraria(horarios: List<String>, onClick: () -> Unit) {
    Box() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // 4 colunas costumam ler melhor que 5 em telas menores
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 230.dp), // heightIn é melhor que height fixo
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(horarios) { horario ->
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF8F9FA))
                        .clickable { onClick() }
                        .border(1.dp, Color(0xFFE9ECEF), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = horario,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF495057)
                    )
                    Text(
                        text = "100", // Unidade ou quantidade sutil
                        fontSize = 14.sp,
                        color = Color(0xFF0BA884),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun FimDeProducao(onClick: () -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                .background(Color.White, RoundedCornerShape(12.dp))
                // O clickable deve vir antes do padding para a área de toque ser completa
                .clickable { onClick() }
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LinhaChaveValor(
                chave = "Volume do Barril:",
                valor = "50L",
                modifier = Modifier.fillMaxWidth()
            )

            LinhaChaveValor(
                chave = "Volume necessário:",
                valor = "458 hl",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp))
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "VERIFIQUE O BUFFER",
                    color = Color(0xFFB71C1C),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

