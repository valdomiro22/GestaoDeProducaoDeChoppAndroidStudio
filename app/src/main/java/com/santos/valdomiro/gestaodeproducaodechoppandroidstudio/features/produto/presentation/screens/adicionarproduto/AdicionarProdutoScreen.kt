package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.adicionarproduto

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.CarregandoComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.CustomOutlinedTextField
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarProdutoScreen(
    viewModel: AdicionarProdutoViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
            Toast.makeText(context, "Produto salvo", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Barril") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6450A1), // Sua cor de fundo (Ex: Roxo)
                    titleContentColor = Color.White,    // Cor padrão do título
                    navigationIconContentColor = Color.White // Cor padrão do ícone
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Nome do Produto")
            CustomOutlinedTextField(
                value = state.nome,
                onValueChange = viewModel::onNomeChanged,
                isErro = state.erroNome != null,
                placeholder = "Nome do Produto",
                inputType = KeyboardType.Text,
            )
            if (state.erroNome != null) ErroComponent(state.erroNome!!)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Prazo de Validade (em dias)")
            CustomOutlinedTextField(
                value = state.prazoValidade,
                onValueChange = viewModel::onValidadeChanged,
                isErro = state.erroPrazoValidade != null,
                placeholder = "validade",
                inputType = KeyboardType.Number,
            )
            if (state.erroPrazoValidade != null) ErroComponent(state.erroPrazoValidade!!)
            Spacer(modifier = Modifier.height(24.dp))

            ButtomFillMaxWidth(
                onClick = viewModel::insert,
                nome = "Salvar"
            )

            if (state.isLoading) CarregandoComponent(cor = Color.Magenta)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdicionarProdutoScreen()
}