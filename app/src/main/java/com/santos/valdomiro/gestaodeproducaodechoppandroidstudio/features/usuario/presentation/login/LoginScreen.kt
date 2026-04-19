package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.OutlinedTextFieldSenha
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val navController = LocalNavController.current

    val scrollState = rememberScrollState()  // Para ScrollView
    val state by viewModel.uiState.collectAsState()
    var mostrarSenha by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(Route.ListaDeGradesRoute.route) {
                popUpTo(Route.LoginRoute.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Login",
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)

        ) {
            Text("E-mail")
            OutlinedTextField(
                value = state.email,
                maxLines = 1,
                onValueChange = viewModel::onEmailChanged,
                isError = state.erroEmail != null,
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp)
            )
            if (state.erroEmail != null) ErroComponent(state.erroEmail!!)
            Spacer(modifier = Modifier.height(8.dp))

            Text("Senha")
            OutlinedTextFieldSenha(
                value = state.senha,
                onValueChange = viewModel::onSenhaChanged,
                isErro = state.erroSenha != null,
                placeholder = "Senha",
                isSenhaVisivel = mostrarSenha,
                onVisibilityChange = { mostrarSenha = !mostrarSenha }
            )
            if (state.erroSenha != null) ErroComponent(state.erroSenha!!)
            if (state.erro != null) ErroComponent(state.erro!!)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(50.dp),
            onClick = viewModel::logar,
            enabled = !state.isLoading,
        ) {
            Text("Logar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        if (state.isLoading) CircularProgressIndicator(color = Color.Magenta)
        
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ainda não possui uma conta?")
            TextButton(
                onClick = {
                    navController.navigate(Route.CadastroRoute.route)
                }
            ) {
                Text("Cadastrar")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}