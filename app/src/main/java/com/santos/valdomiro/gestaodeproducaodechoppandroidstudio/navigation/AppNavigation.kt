package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alteraremail.AlterarEmailScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro.CadastroScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.configuracoesdeusuario.ConfiguracoesDeUsuarioScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.login.LoginScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            composable(Screen.Login.route) { LoginScreen() }
            composable(Screen.Cadastro.route) { CadastroScreen() }
            composable(Screen.ConfiguracoesDeUsuario.route) { ConfiguracoesDeUsuarioScreen() }
            composable(Screen.AlterarEmailScreen.route) { AlterarEmailScreen() }
        }
    }
}