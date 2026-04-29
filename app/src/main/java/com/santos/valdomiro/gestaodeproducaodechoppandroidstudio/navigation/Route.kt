package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val route: String,
    val title: String = "",
    val icon: ImageVector? = null
) {

    data object LoginRoute : Route(route = "login", title = "Login")
    data object CadastroRoute : Route(route = "cadastro", title = "Cadastro")
    data object ConfiguracoesDeUsuario :
        Route(route = "configuracoes-usuario", title = "Configurações de Usuário")

    data object AlterarEmailRoute : Route(route = "alterar-email", title = "Alterar Email")
    data object AlterarSenhaRoute : Route(route = "alterar-senha", title = "Alterar Senha")
    data object AlterarNomeRoute : Route(route = "alterar-nome", title = "Alterar Nome")
    data object DeletarContaRoute : Route(route = "deletar-conta", title = "Deletar Conta")
    data object AdicionarBarrilRoute : Route(route = "adicionar-barril", title = "Adicionar Barril")
    data object AdicionarProdutoRoute :
        Route(route = "adicionar-produto", title = "Adicionar Produto")

    data object CalcualrTempoParadaRoute : Route(route = "calcular-tempo-parada", title = "Tempo de parada")
    data object ListaDeBarrisRoute : Route(route = "lista-barris", title = "lista de barris")
    data object ListaDeProdutosRoute : Route(route = "lista-produtos", title = "lista de produtos")
    data object ListaDeGradesRoute : Route(route = "lista-grades", title = "lista de grades")
    data object AdicionarGradeRoute : Route(route = "adicionar-grade", title = "Adicionar grade")
    data object AdicionarQtHorariaRoute :
        Route(route = "adicionar-qthoraria", title = "Adicionar Qt Horária")

//    data object AdicionarProducaoRoute : Route(route = "adicionar-producao", title = "Adicionar produção")

    data object AdicionarProducaoRoute :
        Route(route = "adicionar-producao/{gradeId}", title = "Adicionar produção") {
        fun criarRota(gradeId: String) =
            "adicionar-producao/$gradeId"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object HomeRoute : Route(route = "home/{producaoId}", title = "Home") {
        fun criarRota(id: String) =
            "home/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object ListaDeProducoesRoute :
        Route(route = "lista-producoes/{gradeId}", title = "Lista de Produções") {
        fun criarRota(id: String) =
            "lista-producoes/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object AtualizarBarril :
        Route(route = "atualizar_barril/{barrilId}", title = "Atualizar Barril") {
        fun criarRota(id: String) =
            "atualizar_barril/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object AtualizarProdutoRoute :
        Route(route = "atualizar_produto/{produtoId}", title = "Atualizar Produto") {
        fun criarRota(id: String) =
            "atualizar_produto/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object AtualizarGradeRoute :
        Route(route = "atualizar_grade/{gradeId}", title = "Atualizar Grade") {
        fun criarRota(id: String) =
            "atualizar_grade/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object AtualizarProducaoRoute :
        Route(route = "atualizar_producao/{producaoId}", title = "Atualizar Produção") {
        fun criarRota(id: String) =
            "atualizar_producao/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object SimularFimProducaoRoute :
        Route(route = "simular-fim-producao/{producaoId}", title = "Fim de produção") {
        fun criarRota(id: String) =
            "simular-fim-producao/$id"  // Uma função ajudante para montar a rota na hora do clique
    }

    data object MainDrawerRoute : Route(
        route = "main_drawer",
        title = "Principal"
    )

}