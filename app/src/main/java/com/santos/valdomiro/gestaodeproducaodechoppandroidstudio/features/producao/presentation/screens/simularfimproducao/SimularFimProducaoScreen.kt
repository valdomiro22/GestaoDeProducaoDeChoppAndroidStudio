package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.simularfimproducao

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.buscarbarril.BuscarBarrilViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.listadebarris.ListaBarrisViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.components.CardNomeValor
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.buscarproducao.BuscarProducaoViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.CustomOutlinedTextField
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimularFimProducaoScreen(
    producaoId: String,
    viewModel: SimularFimProducaoViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.carregarDadosIniciais(producaoId = producaoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simular fim de produção") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Voltar"
                        )
                    }
                },
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6450A1),
                    titleContentColor = Color.White
                )
            )
        }
    ) { contentPadding ->
        when {
            state.producao != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(10.dp)
                ) {
                    Text("Quantidade programada")
                    CustomOutlinedTextField(
                        value = state.qtProgramada,
                        onValueChange = viewModel::onQtProgramadaChanged,
                        placeholder = "Ex: 392",
                        isErro = state.erroQtProgramada != null,
                        inputType = KeyboardType.Number
                    )
                    if (state.erroQtProgramada != null) ErroComponent(state.erroQtProgramada!!)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Quantidade produzida")
                    CustomOutlinedTextField(
                        value = state.qtProduzida,
                        onValueChange = viewModel::onQtProduzidaChanged,
                        placeholder = "Ex: 392",
                        isErro = state.erroQtProduzida != null,
                        inputType = KeyboardType.Number
                    )
                    if (state.erroQtProduzida != null) ErroComponent(state.erroQtProduzida!!)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Nível maximo do Buffer")
                    CustomOutlinedTextField(
                        value = state.nivelMaxTanque,
                        onValueChange = viewModel::onNivelMaxChanged,
                        placeholder = "Ex: 392",
                        isErro = state.erroNivelMaxTanque != null,
                        inputType = KeyboardType.Number
                    )
                    if (state.erroNivelMaxTanque != null) ErroComponent(state.erroNivelMaxTanque!!)
                    Spacer(modifier = Modifier.height(16.dp))

                    ButtomFillMaxWidth(
                        nome = "Simular",
                        onClick = { viewModel.simular() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    CardNomeValor(titulo = "Tipo de barril: ", valor = "30L")
                    Spacer(modifier = Modifier.height(8.dp))
                    CardNomeValor(
                        titulo = "Volume necessário: ",
                        valor = if (state.vlNecessario.isNullOrBlank()) "-" else "${state.vlNecessario} hl"
                    )
                }
            }

            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(top = 16.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.erro != null -> {
                ErroComponent(mensagem = (state.erro!!))
            }
        }

    }
}