package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String = "",
    val icon: ImageVector? = null
) {

    object LoginScreen : Screen("login")
    object CadastroScreen : Screen("cadastro")
    object ConfiguracoesDeUsuario : Screen("configuracoes-usuario")
    object AlterarEmailScreen : Screen("alterar-email")
    object AlterarSenhaScreen : Screen("alterar-senha")
    object AlterarNomeScreen : Screen("alterar-nome")
    object DeletarContaScreen : Screen("deletar-conta")
    object AdicionarBarrilScreen : Screen("adicionar-barril")
    object AdicionarProdutoScreen : Screen("adicionar-produto")
    object HomeScreen : Screen("home")
    object ListaDeBarrisScreen : Screen("lista-barris")
//    data class AtualizarBarrilScreen(val barrilId: String) : Screen("editar_barril/{barrilId}") {
//        fun createRoute(id: String) = "editar_barril/$id"
//    }
object AtualizarBarril : Screen("atualizar_barril/{barrilId}") {
    // Uma função ajudante para montar a rota na hora do clique
    fun criarRota(id: String) = "atualizar_barril/$id"
}

}