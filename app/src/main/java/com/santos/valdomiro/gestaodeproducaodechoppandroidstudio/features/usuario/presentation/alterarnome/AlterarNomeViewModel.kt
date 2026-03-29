package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.alterarnome

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase.UpdateNomeUsuarioUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro.CadastroUsuarioState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlterarNomeViewModel @Inject constructor(
    private val updateNomeUsuarioUseCase: UpdateNomeUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlterarNomeState())
    val uiState = _uiState.asStateFlow()

    fun onNomeChanged(value: String) {
        _uiState.update { it.copy(nome = value, erroNome = null) }
    }

    fun onSobrenomeChanged(value: String) {
        _uiState.update { it.copy(sobrenome = value, erroSobrenome = null) }
    }

    fun alterar() {
        val currenteState = _uiState.value

        if (!validar(currenteState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null ) }

            val result = updateNomeUsuarioUseCase(
                novoNome = currenteState.nome,
                novoSobrenome = currenteState.sobrenome,
            )

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { erro ->
                _uiState.update { it.copy(isLoading = false, erro = erro.toUserMessage()) }
            }
        }
    }

    private fun validar(state: AlterarNomeState): Boolean {
        var isValid = true
        var newState = state

        if (state.nome.isBlank()) {
            isValid = false
            newState = newState.copy(erroNome = "Digite o nome")
        }

        if (state.sobrenome.isBlank()) {
            isValid = false
            newState = newState.copy(erroSobrenome = "Digite o sobrenome")
        }

        _uiState.update { newState }
        return isValid;
    }
}
