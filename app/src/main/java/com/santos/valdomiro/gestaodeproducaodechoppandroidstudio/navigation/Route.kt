package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val route: String,
    val title: String = "",
    val icon: ImageVector? = null
) {

    object LoginRoute : Route(route = "login", title = "Login")
    object CadastroRoute : Route(route = "cadastro", title = "Cadastro")
    object ConfiguracoesDeUsuario : Route(route = "configuracoes-usuario", title = "Configurações de Usuário")
    object AlterarEmailRoute : Route(route = "alterar-email", title = "Alterar Email")
    object AlterarSenhaRoute : Route(route = "alterar-senha", title = "Alterar Senha")
    object AlterarNomeRoute : Route(route = "alterar-nome", title = "Alterar Nome")
    object DeletarContaRoute : Route(route = "deletar-conta", title = "Deletar Conta")
    object AdicionarBarrilRoute : Route(route = "adicionar-barril", title = "Adicionar Barril")
    object AdicionarProdutoRoute : Route(route = "adicionar-produto", title = "Adicionar Produto")
    object HomeRoute : Route(route = "home", title = "Home")
    object ListaDeBarrisRoute : Route(route = "lista-barris", title = "lista de barris")
    object ListaDeProdutosRoute : Route(route = "lista-produtos", title = "lista de produtos")
    object ListaDeGradesRoute : Route(route = "lista-grades", title = "lista de grades")
    object AdicionarGradeRoute : Route(route = "adicionar-grade", title = "Adicionar grade")
    object AdicionarProducaoRoute : Route(route = "adicionar-producao", title = "Adicionar produção")
    object ListaDeProducoesRoute : Route(route = "lista-producoess", title = "Lista de produções")
    object AdicionarQtHorariaRoute : Route(route = "adicionar-qthoraria", title = "Adicionar Qt Horária")

    object AtualizarBarril : Route(route = "atualizar_barril/{barrilId}", title = "Atualizar Barril") {
        fun criarRota(id: String) = "atualizar_barril/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    object AtualizarProdutoRoute : Route(route = "atualizar_produto/{produtoId}", title = "Atualizar Produto") {
        fun criarRota(id: String) = "atualizar_produto/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    object AtualizarGradeRoute : Route(route = "atualizar_grade/{gradeId}", title = "Atualizar Grade") {
        fun criarRota(id: String) = "atualizar_grade/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    object AtualizarProducaoRoute : Route(route = "atualizar_producao/{producaoId}", title = "Atualizar Produção") {
        fun criarRota(id: String) = "atualizar_producao/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

}