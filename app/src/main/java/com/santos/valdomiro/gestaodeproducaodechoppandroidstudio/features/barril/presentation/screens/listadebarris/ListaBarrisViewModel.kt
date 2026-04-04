package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.screens.listadebarris

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.entity.BarrilEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.useCase.GetListaBarrisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaBarrisViewModel @Inject constructor(
    private val getListaBarrisUseCase: GetListaBarrisUseCase,
    private val deleteBarrisUseCase: GetListaBarrisUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<BarrilEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = getListaBarrisUseCase()

            result.onSuccess { listaBarris ->
                _uiState.value = UiState.Success(listaBarris)
            }.onFailure {
                val mensagem = it.message ?: "Erro ao carregar lista de barris"
                _uiState.value = UiState.Error(mensagem)
            }
        }
    }

//    fun deleteBarril(id: String) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading   // ou um loading parcial se preferir
//
//            deleteBarrisUseCase(id)
//                .onSuccess {
//                    getAll()                   // recarrega a lista após deletar
//                    // ou atualiza a lista localmente (mais eficiente)
//                }
//                .onFailure { exception ->
//                    _uiState.value = UiState.Error(
//                        exception.message ?: "Erro ao excluir barril"
//                    )
//                }
//        }
//    }
}