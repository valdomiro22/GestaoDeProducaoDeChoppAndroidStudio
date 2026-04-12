package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.listaproducoes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase.DeleteProducaoUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase.GetAllProducoesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaProducoesViewModel @Inject constructor(
    private val listaProducoesUseCase: GetAllProducoesUseCase,
    private val deleteProducaUseCase: DeleteProducaoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProducaoEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = listaProducoesUseCase()

            result.onSuccess { listaProducoes ->
                _uiState.value = UiState.Success(listaProducoes)
            }.onFailure {
                val mensagem = it.message ?: "Erro ao carregar lista de produções"
                _uiState.value = UiState.Error(mensagem)
            }
        }
    }

    fun deleteProducao(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            deleteProducaUseCase(id = id)
                .onSuccess { getAll() }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao excluir produção"
                    )
                }

        }
    }
}