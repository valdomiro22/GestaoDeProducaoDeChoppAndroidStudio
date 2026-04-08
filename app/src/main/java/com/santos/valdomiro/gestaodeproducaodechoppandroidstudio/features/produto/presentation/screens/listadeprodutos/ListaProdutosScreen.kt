package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.listadeprodutos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.components.ItemListaProduto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.atualizarproduto.AtualizarProdutoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaProdutosScreen(
    viewModel: ListaProdutosViewModel = hiltViewModel()
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
                title = { Text("Lista de Barris") },
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
                    navController.navigate(Route.AdicionarProdutoRoute.route)
                },
                containerColor = Color(0xFF6450A1),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar barril"
                )
            }
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.isError -> {
                ErroComponent(
                    mensagem = (state as? UiState.Error)?.message
                        ?: "Erro desconhecido ao listar produtos"
                )
            }

            state.isSuccess -> {
                val lista: List<ProdutoEntity> = state.getOrNull() ?: emptyList()

                if (lista.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nenhum produto cadastrado ainda")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = lista,
                            key = { it.id ?: it.nome }
                        ) { produto ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                            ) {
                                ItemListaProduto(
                                    produto = produto,
                                    onDeletarClick = { viewModel.deleteProduto(produto.id!!) },
                                    onEditarClick = {
                                        navController.navigate(
                                            Route.AtualizarProdutoRoute.criarRota(
                                                produto.id!!
                                            )
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