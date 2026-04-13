package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.adicionarproduto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase.InsertProdutoUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdicionarProdutoViewModel @Inject constructor(
    private val insertProdutoUseCase: InsertProdutoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdicionarProdutoState())
    val uiState = _uiState.asStateFlow()

    fun onNomeChanged(value: String) {
        _uiState.update { it.copy(nome = value, erroNome = null) }
    }

    fun onValidadeChanged(value: String) {
        val filtered = value.filter { it.isDigit() }
        _uiState.update { it.copy(prazoValidade = filtered, erroPrazoValidade = null) }
    }

    fun insert() {
        val currentState = _uiState.value

        if (!validar(currentState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            val validadeInt = currentState.prazoValidade.toIntOrNull()
                ?: run {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroPrazoValidade = "Prazo de validade inválido"
                        )
                    }
                    return@launch
                }

            if (validadeInt <= 0) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erroPrazoValidade = "Prazo de validade deve ser maior do que zero"
                    )
                }
                return@launch
            }

            val produto = ProdutoEntity(
                nome = currentState.nome,
                prazoValidade = validadeInt
            )

            val result = insertProdutoUseCase(produto = produto)
            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, erro = error.toUserMessage()) }
            }
        }
    }

    private fun validar(state: AdicionarProdutoState): Boolean {
        var isValid = true
        var newState = state

        if (state.nome.isBlank()) {
            isValid = false
            newState = newState.copy(erroNome = "Digite o nome")
        }

        if (state.prazoValidade.isBlank()) {
            isValid = false
            newState = newState.copy(erroPrazoValidade = "Digite a validade")
        }

        _uiState.update { newState }
        return isValid;
    }
}