package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.listadegrades

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer.AppDrawer
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.components.InfoGradeDialog
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.components.ItemListaGrade
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaGradesScreen(
    onOpenDrawer: () -> Unit,
    viewModel: ListaGradesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    var showInfoDialog by remember { mutableStateOf(false) }

    // CONFIGURAÇÃO DO DRAWER
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    LaunchedEffect(Unit) {
        viewModel.getAll()
    }


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
                    title = { Text("Lista de Grades") },   // ← Corrigido: era "Lista de Barris"
                    windowInsets = WindowInsets(0),

                    // Botão para abrir o Drawer
                    navigationIcon = {
                        IconButton(
                            onClick = { onOpenDrawer() }  // Abre o drawer
                        ) {
                            // Troquei o ArrowBack pelo ícone de Menu
                            Icon(
                                Icons.Default.Menu,
                                tint = Color.White,
                                contentDescription = "Abrir Menu"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF6450A1),
                        titleContentColor = Color.White
                    ),
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Route.AdicionarGradeRoute.route)
                    },
                    containerColor = Color(0xFF6450A1),
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar Grade"
                    )
                }
            }
        ) { innerPadding ->
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.isError -> {
                    ErroComponent(
                        mensagem = (state as? UiState.Error)?.message
                            ?: "Erro desconhecido ao listar grades"
                    )
                }

                state.isSuccess -> {
                    val listaGrades =
                        (state as? UiState.Success<List<GradeEntity>>)?.data ?: emptyList()

                    if (listaGrades.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Nenhuma grade encontrada")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(start = 10.dp, top = 8.dp, end = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = listaGrades,
                                key = { it.id ?: it.numero }
                            ) { grade ->
                                Card {
                                    ItemListaGrade(
                                        grade = grade,
                                        navController = navController,
                                        onDeletarClick = { viewModel.deleteGrade(grade.id!!) },
                                        onDetalheClick = {
                                            showInfoDialog = true
                                        },
                                        onEditarClick = {
                                            navController.navigate(
                                                Route.AtualizarGradeRoute.criarRota(grade.id!!)
                                            )
                                        },
                                    )

                                    if (showInfoDialog) {
                                        InfoGradeDialog(
                                            grade = grade,
                                            onConfirm = {
                                                Toast.makeText(context, "Ok", Toast.LENGTH_SHORT)
                                                    .show()
                                            },
                                            onDismiss = { showInfoDialog = false },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}