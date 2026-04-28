package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.atualizarproducao

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.CarregandoComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.listadebarris.ListaBarrisViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.components.DropdownBarril
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.components.DropdownProduto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.listadeprodutos.ListaProdutosViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.CustomOutlinedTextField
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtualizarProducaoScreen(
    producaoId: String,
    atualizarProducaoViewModel: AtualizarProducaoViewModel = hiltViewModel(),
    produtosViewModel: ListaProdutosViewModel = hiltViewModel(),
    barrisViewModel: ListaBarrisViewModel = hiltViewModel()
) {

    val producaoState by atualizarProducaoViewModel.uiState.collectAsState()
    val produtosUiState by produtosViewModel.uiState.collectAsState()
    val barrisUiState by barrisViewModel.uiState.collectAsState()

    val navController = LocalNavController.current
    val context = LocalContext.current

    // Carrega a lista automaticamente quando entra na tela
    LaunchedEffect(Unit) {
        produtosViewModel.getAll()
        barrisViewModel.getAll()
        atualizarProducaoViewModel.buscarProducao(producaoId)
    }

    LaunchedEffect(producaoState.isSuccess) {
        if (producaoState.isSuccess) {
            navController.popBackStack()
            Toast.makeText(context, "Produção Atualizada", Toast.LENGTH_SHORT).show()
//            producaoViewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atualizar Produção") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6450A1),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = produtosUiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Carregando produtos...",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                is UiState.Success -> {
                    DropdownProduto(
                        listaProdutos = state.data,
                        produtoIdAtual = producaoState.produtoId,
                        onProdutoSelecionado = atualizarProducaoViewModel::onProdutoChanged
                    )
                }

                is UiState.Error -> {
                    Text(
                        text = "Erro ao carregar produtos: ${state.message}",
                        color = Color.Red
                    )
                }

                is UiState.Idle -> {
                    Text("Aguardando carregamento dos produtos...")
                }
            }
            if (producaoState.erroProduto != null) ErroComponent(producaoState.erroProduto!!)
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = barrisUiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Carregando barris...",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                is UiState.Success -> {
                    DropdownBarril(
                        listaBarris = state.data,
                        barrilIdAtual = producaoState.barrilId,
                        onBarrilSelecionado = atualizarProducaoViewModel::onBarrilChanged
                    )
                }

                is UiState.Error -> {
                    Text(
                        text = "Erro ao carregar barris: ${state.message}",
                        color = Color.Red
                    )
                }

                is UiState.Idle -> {
                    Text("Aguardando carregamento dos barris...")
                }
            }
            if (producaoState.erroBarril != null) ErroComponent(producaoState.erroBarril!!)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Quantidade")
            CustomOutlinedTextField(
                value = producaoState.quantidade,
                onValueChange = atualizarProducaoViewModel::onQuantidadeChanged,
                isErro = producaoState.erroQuantidade!= null,
                inputType = KeyboardType.Text,
                placeholder = "Numero"
            )
            if (producaoState.erroQuantidade != null) ErroComponent(producaoState.erroQuantidade!!)
            Spacer(modifier = Modifier.height(24.dp))

            ButtomFillMaxWidth(
                onClick = atualizarProducaoViewModel::atualizar,
                nome = "Atualizar"
            )

            if (producaoState.isLoading) CarregandoComponent(cor = Color.Magenta)

        }
    }
}