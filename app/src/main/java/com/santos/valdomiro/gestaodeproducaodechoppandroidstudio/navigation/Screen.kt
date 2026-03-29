package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String = "",
    val icon: ImageVector? = null
) {

    object Login : Screen("login")
    object Cadastro : Screen("cadastro")
    object ConfiguracoesDeUsuario : Screen("configuracoes-usuario")
    object AlterarEmailScreen : Screen("alterar-email")

}