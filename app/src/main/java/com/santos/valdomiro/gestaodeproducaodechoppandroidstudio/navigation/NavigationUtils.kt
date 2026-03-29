package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("Não encontrado")
}