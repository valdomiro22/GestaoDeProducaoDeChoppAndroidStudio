package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alterarsenha

data class AlterarSenhaState(
    val novaSenha: String = "",
    val senhaAtual: String = "",

    val erroNovaSenha: String? = null,
    val erroSenhaAtual: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)