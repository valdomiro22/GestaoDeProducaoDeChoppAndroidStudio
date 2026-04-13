package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.presentation.screens.adicionarquantidadehoraria

data class AdicionarQtHorariaState(
    val turnoId: String? = null,
    val producaoId: String? = null,
    val horarioReferente: String? = null,
    val quantidade: String = "",

    val erroQuantidade: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)