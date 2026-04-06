package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.atualizarbarril

data class AtualizarBarrilState(
    val nome: String = "",
    val volume: String = "",
    val descartavel: Boolean = false,

    val erroNome: String? = null,
    val erroVolume: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)