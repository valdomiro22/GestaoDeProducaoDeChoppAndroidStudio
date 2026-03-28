package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro

data class CadastroUsuarioState(
    val nome: String = "",
    val sobrenome: String = "",
    val email: String = "",
    val senha: String = "",
    val confirmarSenha: String = "",

    val erro: String? = null,
    val erroNome: String? = null,
    val erroSobrenome: String? = null,
    val erroEmail: String? = null,
    val erroSenha: String? = null,
    val erroConfirmarSenha: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)