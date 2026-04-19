package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.listadegrades

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.components.ItemListaGrade
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaGradesScreen(
    viewModel: ListaGradesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.getAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Grades") },   // ← Corrigido: era "Lista de Barris"
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6450A1),
                    titleContentColor = Color.White
                )
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
                                    onEditarClick = {
                                        Log.i(
                                            TAG,
                                            "ListaGradesScreen: ID para atualizar: ${grade.id}"
                                        )
                                        navController.navigate(
                                            Route.AtualizarGradeRoute.criarRota(grade.id!!)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}