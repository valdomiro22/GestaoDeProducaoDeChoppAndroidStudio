package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen.MainDrawerWrapper
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro.CadastroScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.login.LoginScreen

@Composable
fun AuthGraph(
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
            composable(Route.LoginRoute.route) {
                LoginScreen()
            }

            composable(Route.CadastroRoute.route) {
                CadastroScreen()
            }

            composable(Route.MainDrawerRoute.route) {
                MainDrawerWrapper(
                    parentNavController = navController
                )
            }
        }
    }
}