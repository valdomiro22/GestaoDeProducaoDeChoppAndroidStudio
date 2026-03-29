package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alterarnome

data class AlterarNomeState(
    val nome: String = "",
    val sobrenome: String = "",

    val erroNome: String? = null,
    val erroSobrenome: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)