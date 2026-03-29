package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alterarsenha

import android.content.ContextParams
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase.UpdatePasswordUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlterarSenhaViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlterarSenhaState())
    val uiState = _uiState.asStateFlow()

    fun onNovaSenhaChanged(value: String) {
        _uiState.update { it.copy(novaSenha = value, erroNovaSenha = null) }
    }

    fun onSenhaAtualChanged(value: String) {
        _uiState.update { it.copy(senhaAtual = value, erroSenhaAtual = null) }
    }

    fun alterar() {
        val currenteState = _uiState.value

        if (!validar(currenteState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            val result = updatePasswordUseCase(
                newPassword = currenteState.novaSenha,
                currenteState.senhaAtual
            )
            result.onSuccess { uid ->
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

    private fun validar(state: AlterarSenhaState): Boolean {
        var isValid = true
        var newState = state

        if (state.novaSenha.isBlank()) {
            isValid = false
            newState = newState.copy(erroNovaSenha = "Digite uma senha")
        } else if (state.novaSenha.length < 6) {
            isValid = false
            newState = newState.copy(erroNovaSenha = "A senha deve ter pelo menos 6 caracteres")
        }

        if (state.senhaAtual.isBlank()) {
            isValid = false
            newState = newState.copy(erroSenhaAtual = "Digite a senha atual")
        } else if (state.senhaAtual.length < 6) {
            isValid = false
            newState = newState.copy(erroSenhaAtual = "A senha deve ter pelo menos 6 caracteres")
        }

        _uiState.update { newState }
        return isValid;
    }
}