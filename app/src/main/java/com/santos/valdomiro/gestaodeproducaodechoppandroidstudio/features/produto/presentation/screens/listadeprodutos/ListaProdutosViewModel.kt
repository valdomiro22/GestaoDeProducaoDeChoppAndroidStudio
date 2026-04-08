package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.presentation.screens.listadeprodutos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase.DeleteProdutoUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase.GetAllProdutosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaProdutosViewModel @Inject constructor(
    private val getAllProdutosUseCase: GetAllProdutosUseCase,
    private val deleteProdutoUseCase: DeleteProdutoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProdutoEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = getAllProdutosUseCase()

            result.onSuccess { listaProdutos ->
                _uiState.value = UiState.Success(listaProdutos)
            }.onFailure {
                val mensagem = it.message ?: "Erro ao carregar lista de produtos"
                _uiState.value = UiState.Error(mensagem)
            }
        }
    }

    fun deleteProduto(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            deleteProdutoUseCase(id = id)
                .onSuccess { getAll() }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Erro ao excluir produto")
                }
        }
    }
}