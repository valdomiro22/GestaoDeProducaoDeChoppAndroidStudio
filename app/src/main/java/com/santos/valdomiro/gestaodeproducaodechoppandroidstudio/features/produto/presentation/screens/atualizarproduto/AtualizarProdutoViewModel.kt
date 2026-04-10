package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.atualizarproduto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase.GetUmProdutoUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase.UpdateProdutoUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.adicionarproduto.AdicionarProdutoState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AtualizarProdutoViewModel @Inject constructor(
    private val getProdutoUseCase: GetUmProdutoUseCase,
    private val updateProdutoUseCase: UpdateProdutoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AtualizarProdutoState())
    val uiState = _uiState.asStateFlow()

    private var produtoIdAtual: String? = null
    fun buscarProduto(produtoId: String) {
        produtoIdAtual = produtoId

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            getProdutoUseCase(produtoId).fold(
                onSuccess = { produto ->
                    _uiState.update {
                        it.copy(
                            nome = produto.nome,
                            prazoValidade = produto.prazoValidade.toString(),
                            isLoading = false,
                            erro = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erro = error.message ?: "Erro ao buscar produto"
                        )
                    }
                }
            )
        }
    }

    fun onNomeChanged(value: String) {
        _uiState.update { it.copy(nome = value, erroNome = null) }
    }

    fun onValidadeChanged(value: String) {
        val filtered = value.filter { it.isDigit() }
        _uiState.update { it.copy(prazoValidade = value, erroPrazoValidade = null) }
    }

    fun atualizar() {
        val currentState = _uiState.value
        val id = produtoIdAtual ?: return

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
                id = id,
                nome = currentState.nome,
                prazoValidade = validadeInt
            )

            updateProdutoUseCase(id = id, produto = produto).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { erro ->
                    _uiState.update { it.copy(isLoading = false, erro = erro.message) }
                }
            )
        }

    }

    private fun validar(state: AtualizarProdutoState): Boolean {
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