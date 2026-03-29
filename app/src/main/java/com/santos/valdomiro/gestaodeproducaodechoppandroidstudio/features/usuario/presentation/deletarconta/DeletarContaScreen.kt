package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.deletarconta

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
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.CustomOutlinedTextField
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.OutlinedTextFieldSenha
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletarContaScreen(
    viewModel: DeletarContaViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavController.current
    var mostrarSenha by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            Toast.makeText(context, "Conta deletada", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(0) { inclusive = true }  // Limpa tudo do histórico para o usuário não conseguir "voltar" para as configs

            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deletar conta") },
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
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text("Digite seu email e senha para prosseguir com está ação.")
            Spacer(modifier = Modifier.height(24.dp))

            CustomOutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChanged,
                isErro = state.erroEmail != null,
                placeholder = "E-mail",
            )
            if (state.erroEmail != null) ErroComponent(state.erroEmail!!)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextFieldSenha(
                value = state.senha,
                onValueChange = viewModel::onSenhaChanged,
                placeholder = "Senha",
                isErro = state.erroSenha != null,
                onVisibilityChange = { mostrarSenha = !mostrarSenha },
                isSenhaVisivel = mostrarSenha,
            )
            if (state.erroSenha != null) ErroComponent(state.erroSenha!!)
            if (state.erro != null) ErroComponent(state.erro!!)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = viewModel::deletar,
                enabled = !state.isLoading,
            ) {
                Text("Alterar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) CircularProgressIndicator(color = Color.Magenta)
        }
    }
}