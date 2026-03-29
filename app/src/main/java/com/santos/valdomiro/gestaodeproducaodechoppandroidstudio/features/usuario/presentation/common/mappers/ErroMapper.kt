package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AuthException

/**
 * Função global que transforma qualquer erro do sistema
 * em uma mensagem amigável para o usuário brasileiro.
 */
fun Throwable.toUserMessage(): String {
    return when (this) {
        // Erros de Autenticação (AuthException)
        is AuthException.UsuarioNaoEncontrado -> "Este e-mail não está cadastrado no sistema."
        is AuthException.CredenciaisInvalidas -> "E-mail ou senha incorretos. Tente novamente."
        is AuthException.EmailEmUso -> "Este e-mail já está sendo usado por outra conta."
        is AuthException.SenhaFraca -> "A senha deve ter pelo menos 6 caracteres."
        is AuthException.EmailInvalido -> "O formato do e-mail digitado não é válido."
        is AuthException.ReautenticacaoNecessaria -> "Por segurança, faça login novamente para realizar esta alteração."
        is AuthException.ErroDeRede -> "Sem conexão com a internet. Verifique seu Wi-Fi ou dados móveis."

        // Erros Genéricos ou inesperados
        is AuthException.Desconhecido -> "Ocorreu um erro inesperado: ${this.cause?.message ?: "Tente mais tarde"}"

        // Fallback para qualquer outra exceção que não mapeamos
        else -> this.message ?: "Ocorreu um erro desconhecido. Por favor, tente novamente."
    }
}