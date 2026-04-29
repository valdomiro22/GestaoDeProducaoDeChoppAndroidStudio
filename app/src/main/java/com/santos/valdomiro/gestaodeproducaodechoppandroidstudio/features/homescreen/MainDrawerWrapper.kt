package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer.AppDrawer
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer.DrawerViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.MainGraph
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun MainDrawerWrapper(
    parentNavController: NavHostController,
    drawerViewModel: DrawerViewModel = hiltViewModel()
) {
    val drawerNavController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute = drawerNavController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    CompositionLocalProvider(LocalNavController provides drawerNavController) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        scope.launch {
                            drawerState.close()

                            drawerNavController.navigate(route.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    onCloseDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    onLogoutClick = {
                        scope.launch {
                            drawerState.close()

                            drawerViewModel.deslogar()

                            parentNavController.navigate(Route.LoginRoute.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        ) {
            MainGraph(
                navController = drawerNavController,
                startDestination = Route.ListaDeGradesRoute.route,
                onOpenDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}