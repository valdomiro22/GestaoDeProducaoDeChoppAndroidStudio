package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.presentation.screens.listaqthorariaproducao

data class ListaQtHorariaDaProducaoState(
    val producaoId: String? = null,

    val erroProducaoId: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)