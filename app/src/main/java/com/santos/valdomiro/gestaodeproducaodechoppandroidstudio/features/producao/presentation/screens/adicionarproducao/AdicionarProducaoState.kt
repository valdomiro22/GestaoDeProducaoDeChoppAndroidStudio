package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.adicionarproducao

data class AdicionarProducaoState(
    val barrilId: String? = null,
    val barrilNome: String? = null,
    val produtoId: String? = null,
    val produtoNome: String? = null,
    val quantidade: String = "",

    val erroQuantidade: String? = null,
    val erroBarril: String? = null,
    val erroProduto: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)