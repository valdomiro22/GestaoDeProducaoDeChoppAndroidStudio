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
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.adicionargrade.AdicionarGradeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.atualizargrade.AtualizarGradeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.atualizargrade.AtualizarGradeState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.listadegrades.ListaGradesScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen.HomeScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.adicionarproducao.AdicionarProducaoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.adicionarproduto.AdicionarProdutoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.atualizarproduto.AtualizarProdutoScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.listadeprodutos.ListaProdutosScreen
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
            composable(Route.LoginRoute.route) { LoginScreen() }
            composable(Route.CadastroRoute.route) { CadastroScreen() }
            composable(Route.ConfiguracoesDeUsuario.route) { ConfiguracoesDeUsuarioScreen() }
            composable(Route.AlterarEmailRoute.route) { AlterarEmailScreen() }
            composable(Route.AlterarSenhaRoute.route) { AlterarSenhaScreen() }
            composable(Route.AlterarNomeRoute.route) { AlterarNomeScreen() }
            composable(Route.DeletarContaRoute.route) { DeletarContaScreen() }
            composable(Route.AdicionarBarrilRoute.route) { AdicionarBarrilScreen() }
            composable(Route.AdicionarProdutoRoute.route) { AdicionarProdutoScreen() }
            composable(Route.HomeRoute.route) { HomeScreen() }
            composable(Route.ListaDeBarrisRoute.route) { ListaBarrisScreen() }
            composable(Route.ListaDeProdutosRoute.route) { ListaProdutosScreen() }
            composable(Route.ListaDeGradesRoute.route) { ListaGradesScreen() }
            composable(Route.AdicionarGradeRoute.route) { AdicionarGradeScreen() }
            composable(Route.AdicionarProducaoRoute.route) { AdicionarProducaoScreen() }

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

            // Atualizar Produto
            composable(
                // A rota DEVE ter o placeholder {barrilId} explicitamente
                route = "atualizar_produto/{produtoId}",
                arguments = listOf(
                    navArgument("produtoId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val produtoId = backStackEntry.arguments?.getString("produtoId") ?: return@composable
                AtualizarProdutoScreen(produtoId = produtoId)
            }

            // Atualizar Grade
            composable(
                // A rota DEVE ter o placeholder {barrilId} explicitamente
                route = "atualizar_grade/{gradeId}",
                arguments = listOf(
                    navArgument("gradeId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val gradeId = backStackEntry.arguments?.getString("gradeId") ?: return@composable
                AtualizarGradeScreen(gradeId = gradeId)
            }
        }
    }
}