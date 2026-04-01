package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.adicionarbarril

data class AdicionarBarrilState(
    val nome: String = "",
    val volume: String = "",
    val isDescartavel: Boolean = false,

    val erroNome: String? = null,
    val erroVolume: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)