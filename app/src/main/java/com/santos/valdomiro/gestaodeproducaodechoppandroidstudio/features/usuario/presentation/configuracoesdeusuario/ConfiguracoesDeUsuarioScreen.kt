package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.configuracoesdeusuario

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Screen

@Composable
fun ConfiguracoesDeUsuarioScreen(
    viewModel: ConfiguracoesDeUsuarioViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            Toast.makeText(context, "Sessão encerrada", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }  // Limpa tudo do histórico para o usuário não conseguir "voltar" para as configs

            }
        } else if (uiState is UiState.Error) {
            val message = (uiState as UiState.Error).message
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetState()
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

        val isLoading = uiState is UiState.Loading

        ButtomFillMaxWidth(
            onClick = { viewModel.deslogar() },
            nome = if (isLoading) "Saindo..." else "Deslogar"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = {},
            nome = "Alterar Nome"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = {},
            nome = "Alterar E-mail"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = {},
            nome = "Alterar Senha"
        )
        Spacer(modifier = Modifier.height(16.dp))


        ButtomFillMaxWidth(
            onClick = {},
            nome = "Excluir Conta"
        )
    }
}