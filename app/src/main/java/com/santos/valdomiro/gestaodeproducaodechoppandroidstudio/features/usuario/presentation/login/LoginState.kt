package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.login

data class LoginState(
    val email: String = "",
    val senha: String = "",

    val erroEmail: String? = null,
    val erroSenha: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)