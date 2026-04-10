package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val route: String,
    val title: String = "",
    val icon: ImageVector? = null
) {

    object LoginRoute : Route("login")
    object CadastroRoute : Route("cadastro")
    object ConfiguracoesDeUsuario : Route("configuracoes-usuario")
    object AlterarEmailRoute : Route("alterar-email")
    object AlterarSenhaRoute : Route("alterar-senha")
    object AlterarNomeRoute : Route("alterar-nome")
    object DeletarContaRoute : Route("deletar-conta")
    object AdicionarBarrilRoute : Route("adicionar-barril")
    object AdicionarProdutoRoute : Route("adicionar-produto")
    object HomeRoute : Route("home")
    object ListaDeBarrisRoute : Route("lista-barris")
    object ListaDeProdutosRoute : Route("lista-produtos")
    object ListaDeGradesRoute : Route("lista-grades")
    object AdicionarGradeRoute : Route("adicionar-grade")

    object AtualizarBarril : Route("atualizar_barril/{barrilId}") {
        fun criarRota(id: String) = "atualizar_barril/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    object AtualizarProdutoRoute : Route("atualizar_produto/{produtoId}") {
        fun criarRota(id: String) = "atualizar_produto/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    object AtualizarGradeRoute : Route("atualizar_grade/{gradeId}") {
        fun criarRota(id: String) = "atualizar_grade/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

}