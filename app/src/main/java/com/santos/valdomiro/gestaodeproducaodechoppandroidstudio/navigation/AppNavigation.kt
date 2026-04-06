package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.adicionarbarril.AdicionarBarrilScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.atualizarbarril.AtualizarBarrilScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.listadebarris.ListaBarrisScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen.HomeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alteraremail.AlterarEmailScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alterarnome.AlterarNomeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alterarsenha.AlterarSenhaScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro.CadastroScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.configuracoesdeusuario.ConfiguracoesDeUsuarioScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.deletarconta.DeletarContaScreen
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
            composable(Screen.LoginScreen.route) { LoginScreen() }
            composable(Screen.CadastroScreen.route) { CadastroScreen() }
            composable(Screen.ConfiguracoesDeUsuario.route) { ConfiguracoesDeUsuarioScreen() }
            composable(Screen.AlterarEmailScreen.route) { AlterarEmailScreen() }
            composable(Screen.AlterarSenhaScreen.route) { AlterarSenhaScreen() }
            composable(Screen.AlterarNomeScreen.route) { AlterarNomeScreen() }
            composable(Screen.DeletarContaScreen.route) { DeletarContaScreen() }
            composable(Screen.AdicionarBarrilScreen.route) { AdicionarBarrilScreen() }
            composable(Screen.HomeScreen.route) { HomeScreen() }
            composable(Screen.ListaDeBarrisScreen.route) { ListaBarrisScreen() }

//            // Atualizar Barril
//            composable(
//                route = Screen.AtualizarBarrilScreen("dummy").route,
//                arguments = listOf(
//                    navArgument("barrilId") {
//                        type = NavType.StringType
//                    }
//                )
//            ) { backStackEntry ->
//                val barrilId = backStackEntry.arguments?.getString("barrilId")
//                    ?: return@composable
//
//                AtualizarBarrilScreen(barrilId = barrilId)
//            }

            // Atualizar Barril
            composable(
                // A rota DEVE ter o placeholder {barrilId} explicitamente
                route = "atualizar_barril/{barrilId}",
                arguments = listOf(
                    navArgument("barrilId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val barrilId = backStackEntry.arguments?.getString("barrilId") ?: return@composable

                AtualizarBarrilScreen(barrilId = barrilId)
            }
        }
    }
}