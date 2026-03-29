package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.AppNavigation

@Composable
fun MainAppScreen(
    startDestination: String
) {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        AppNavigation(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        )
    }
}