package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.screens.CalcularTempoParadaScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.adicionarbarril.AdicionarBarrilScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.atualizarbarril.AtualizarBarrilScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.listadebarris.ListaBarrisScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.adicionargrade.AdicionarGradeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.atualizargrade.AtualizarGradeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.atualizargrade.AtualizarGradeState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.listadegrades.ListaGradesScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen.HomeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.adicionarproducao.AdicionarProducaoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.atualizarproducao.AtualizarProducaoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.atualizarproducao.AtualizarProducaoState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.listaproducoes.ListaProducoesScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.simularfimproducao.SimularFimProducaoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.adicionarproduto.AdicionarProdutoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.atualizarproduto.AtualizarProdutoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.listadeprodutos.ListaProdutosScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.presentation.screens.adicionarquantidadehoraria.AdicionarQtHorariaScreen
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
            // ==================== TELAS DE AUTENTICAÇÃO ====================
            composable(Route.LoginRoute.route) {
                LoginScreen()
            }
            composable(Route.CadastroRoute.route) {
                CadastroScreen()
            }

            // ==================== TELAS DE USUÁRIO ====================
            composable(Route.ConfiguracoesDeUsuario.route) {
                ConfiguracoesDeUsuarioScreen()
            }
            composable(Route.AlterarEmailRoute.route) {
                AlterarEmailScreen()
            }
            composable(Route.AlterarSenhaRoute.route) {
                AlterarSenhaScreen()
            }
            composable(Route.AlterarNomeRoute.route) {
                AlterarNomeScreen()
            }
            composable(Route.DeletarContaRoute.route) {
                DeletarContaScreen()
            }

            // ==================== TELAS PRINCIPAIS ====================
            composable(
                route = "home/{producaoId}",
                arguments = listOf(navArgument("producaoId") { type = NavType.StringType })
            ) { backStackEntry ->
                val producaoId = backStackEntry.arguments?.getString("producaoId") ?: return@composable
                HomeScreen(producaoId = producaoId)
            }

            // ==================== BARRIS ====================
            composable(Route.ListaDeBarrisRoute.route) {
                ListaBarrisScreen()
            }
            composable(Route.AdicionarBarrilRoute.route) {
                AdicionarBarrilScreen()
            }
            composable(
                route = "atualizar_barril/{barrilId}",
                arguments = listOf(navArgument("barrilId") { type = NavType.StringType })
            ) { backStackEntry ->
                val barrilId = backStackEntry.arguments?.getString("barrilId") ?: return@composable
                AtualizarBarrilScreen(barrilId = barrilId)
            }

            // ==================== PRODUTOS ====================
            composable(Route.ListaDeProdutosRoute.route) {
                ListaProdutosScreen()
            }
            composable(Route.AdicionarProdutoRoute.route) {
                AdicionarProdutoScreen()
            }
            composable(
                route = "atualizar_produto/{produtoId}",
                arguments = listOf(navArgument("produtoId") { type = NavType.StringType })
            ) { backStackEntry ->
                val produtoId = backStackEntry.arguments?.getString("produtoId") ?: return@composable
                AtualizarProdutoScreen(produtoId = produtoId)
            }

            // ==================== GRADES (o que você quer adicionar) ====================
            composable(Route.ListaDeGradesRoute.route) {
                ListaGradesScreen()
            }
            composable(Route.AdicionarGradeRoute.route) {
                AdicionarGradeScreen()
            }
            composable(
                route = "atualizar_grade/{gradeId}",
                arguments = listOf(navArgument("gradeId") { type = NavType.StringType })
            ) { backStackEntry ->
                val gradeId = backStackEntry.arguments?.getString("gradeId") ?: return@composable
                AtualizarGradeScreen(gradeId = gradeId)
            }

            // ==================== PRODUÇÕES ====================
            composable(
                route = "lista-producoes/{gradeId}",
                arguments = listOf(navArgument("gradeId") { type = NavType.StringType })
            ) { backStackEntry ->
                val gradeId = backStackEntry.arguments?.getString("gradeId") ?: return@composable
                ListaProducoesScreen(gradeId = gradeId)
            }
            composable(
                route = "adicionar-producao/{gradeId}",
                arguments = listOf(navArgument("gradeId") { type = NavType.StringType })
            ) { backStackEntry ->
                val gradeId = backStackEntry.arguments?.getString("gradeId") ?: return@composable
                AdicionarProducaoScreen(gradeId = gradeId)
            }
//            composable(Route.AdicionarProducaoRoute.route) {
//                AdicionarProducaoScreen()
//            }
            composable(
                route = "atualizar_producao/{producaoId}",
                arguments = listOf(navArgument("producaoId") { type = NavType.StringType })
            ) { backStackEntry ->
                val producaoId = backStackEntry.arguments?.getString("producaoId") ?: return@composable
                AtualizarProducaoScreen(producaoId = producaoId)
            }
            composable(
                route = "simular-fim-producao/{producaoId}",
                arguments = listOf(navArgument("producaoId") { type = NavType.StringType })
            ) { backStackEntry ->
                val producaoId = backStackEntry.arguments?.getString("producaoId") ?: return@composable
                SimularFimProducaoScreen(producaoId = producaoId)
            }

            // ==================== OUTRAS ====================
            composable(Route.AdicionarQtHorariaRoute.route) {
                AdicionarQtHorariaScreen()
            }
            composable(Route.CalcualrTempoParadaRoute.route) {
                CalcularTempoParadaScreen()
            }
        }
    }
}