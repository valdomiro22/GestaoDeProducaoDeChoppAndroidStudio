package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alteraremail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase.UpdateEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlterarEmailViewModel @Inject constructor(
    private val updateEmailUseCase: UpdateEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlterarEmailState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, erroEmail = null) }
    }

    fun onSenhaChanged(value: String) {
        _uiState.update { it.copy(senha = value, erroSenha = null) }
    }

    fun alterar() {
        val currentState = _uiState.value

        if (!validar(currentState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            try {
                updateEmailUseCase(newEmail = currentState.email, currentPassword = currentState.senha)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erro = e.message ?: "Erro ao alterar e-mail de usuario"
                    )
                }
            }
        }
    }

    private fun validar(state: AlterarEmailState): Boolean {
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
            newState = newState.copy(erroSenha = "Digite uma senha")
        } else if (state.senha.length < 6) {
            isValid = false
            newState = newState.copy(erroSenha = "A senha deve ter pelo menos 6 caracteres")
        }

        _uiState.update { newState }
        return isValid;
    }

}