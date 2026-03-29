package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alterarsenha

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.OutlinedTextFieldSenha
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlterarSenhaScreen(
    viewModel: AlterarSenhaViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavController.current
    var mostrarSenhaAtual by remember { mutableStateOf(false) }
    var mostrarNovaSenha by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
            Toast.makeText(context, "Senha alterada ", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alterar Senha") },
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text("Digite a nova senha e confirme com sua senha atual para prosseguir com esta ação.")
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextFieldSenha(
                value = state.novaSenha,
                onValueChange = viewModel::onNovaSenhaChanged,
                placeholder = "Nova senha",
                isErro = state.erroNovaSenha != null,
                onVisibilityChange = { mostrarNovaSenha = !mostrarNovaSenha },
                isSenhaVisivel = mostrarNovaSenha,
            )
            if (state.erroNovaSenha != null) ErroComponent(state.erroNovaSenha!!)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextFieldSenha(
                value = state.senhaAtual,
                onValueChange = viewModel::onSenhaAtualChanged,
                placeholder = "Senha atual",
                isErro = state.erroSenhaAtual != null,
                onVisibilityChange = { mostrarSenhaAtual = !mostrarSenhaAtual },
                isSenhaVisivel = mostrarSenhaAtual,
            )
            if (state.erroSenhaAtual != null) ErroComponent(state.erroSenhaAtual!!)
            if (state.erro != null) ErroComponent(state.erro!!)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = viewModel::alterar,
                enabled = !state.isLoading,
            ) {
                Text("Alterar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) CircularProgressIndicator(color = Color.Magenta)
        }
    }
}