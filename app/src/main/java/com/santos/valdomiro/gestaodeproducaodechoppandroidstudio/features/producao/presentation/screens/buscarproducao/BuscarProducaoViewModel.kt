package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.buscarproducao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase.GetOneProducaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarProducaoViewModel @Inject constructor(
    private val getUmaProducaoUseCase: GetOneProducaoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ProducaoEntity>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun buscarProducao(producaoId: String) {

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getUmaProducaoUseCase(producaoId).fold(
                onSuccess = { producao ->
                    _uiState.value = UiState.Success(producao)
                },
                onFailure = { erro ->
                    _uiState.value = UiState.Error(erro.message ?: "Erro ao buscar produção")
                }
            )
        }

    }

}