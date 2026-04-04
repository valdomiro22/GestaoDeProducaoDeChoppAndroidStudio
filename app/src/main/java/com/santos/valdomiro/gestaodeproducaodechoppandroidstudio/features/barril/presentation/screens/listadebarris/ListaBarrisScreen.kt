package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.listadebarris

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.entity.BarrilEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.components.ItemListaBarril
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaBarrisScreen(
    viewModel: ListaBarrisViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.getAll()
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.isError -> {
            ErroComponent(
                mensagem = (state as? UiState.Error)?.message
                    ?: "Erro desconhecido ao listar barris"
            )
        }

        state.isSuccess -> {
            val lista: List<BarrilEntity> = state.getOrNull() ?: emptyList()


            if (lista.isEmpty()) {
                // Opcional: mostra mensagem de lista vazia
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhum barril cadastrado ainda")
                    Log.d(TAG, "ListaBarrisScreen: Lista Vazia")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Log.d(TAG, "ListaBarrisScreen: Erro qualquer")
                    items(
                        items = lista,
                        key = { it.id ?: it.nome }        // chave única (boa prática)
                    ) { barril ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            ItemListaBarril(barril)
                        }
                    }
                }
            }

        }
    }


}