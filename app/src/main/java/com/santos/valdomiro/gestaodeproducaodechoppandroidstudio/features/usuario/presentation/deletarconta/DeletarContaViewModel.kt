package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.deletarconta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase.DeleteUserUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alteraremail.AlterarEmailState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletarContaViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DeletarContaState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, erroEmail = null) }
    }

    fun onSenhaChanged(value: String) {
        _uiState.update { it.copy(senha = value, erroSenha = null) }
    }

    fun deletar() {
        val currenteState = _uiState.value

        if (!validar(currenteState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            val result = deleteUserUseCase(email = currenteState.email, password = currenteState.senha)

            result.onSuccess {
                _uiState.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
            }.onFailure { erro ->
                _uiState.update {
                    it.copy(isLoading = false, erro = erro.toUserMessage())
                }
            }
        }
    }

    private fun validar(state: DeletarContaState): Boolean {
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