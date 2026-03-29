package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase.LogarUsuarioUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro.CadastroUsuarioState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logarUsuarioUseCase: LogarUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, erroEmail = null) }
    }

    fun onSenhaChanged(value: String) {
        _uiState.update { it.copy(senha = value, erroSenha = null) }
    }

    fun logar() {
        val currentState = _uiState.value

        if (!validar(currentState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            try {
                logarUsuarioUseCase(currentState.email, currentState.senha)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, erro = e.message ?: "Erro ao logar usuário") }
            }
        }
    }

    private fun validar(state: LoginState): Boolean {
        var isValid = true
        var newState = state

        if (state.email.isBlank()) {
            isValid = false
            newState = newState.copy(erroEmail = "Digite o e-mail")
        } else if (!state.email.contains("@") || !state.email.contains(".")) {
            isValid = false
            newState = newState.copy(erroEmail = "Digite um e-mail válido")
        }

        if (state.senha.isBlank()) {
            isValid = false
            newState = newState.copy(erroSenha = "Digite a senha")
        } else if (state.senha.length < 6) {
            isValid = false
            newState = newState.copy(erroSenha = "Senha inválida")
        }

        _uiState.update { newState }
        return isValid;
    }
}