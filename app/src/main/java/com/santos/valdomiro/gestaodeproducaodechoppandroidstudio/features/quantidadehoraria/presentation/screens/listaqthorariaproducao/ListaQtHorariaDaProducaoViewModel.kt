package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.presentation.screens.listaqthorariaproducao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.usecase.GetAllQtHorariaDaProducaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaQtHorariaDaProducaoViewModel @Inject constructor(
    private val getQtHorariaDaProducao: GetAllQtHorariaDaProducaoUseCase
) : ViewModel() {

    // A chave (String) será o seu horarioReferente (ex: "09:00")
    private val _uiState = MutableStateFlow<UiState<Map<String, QuantidadeHorariaEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun carregarDadosDaProducao(producaoId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = getQtHorariaDaProducao(producaoId = producaoId)

            result.onSuccess { lista ->
                // Transforma a lista em um Mapa para busca rápida na UI
                val mapaDeHorarios = lista.associateBy { it.horarioReferente }
                _uiState.value = UiState.Success(mapaDeHorarios)
            }.onFailure {
                val mensagem = it.message ?: "Erro ao carregar quantidades"
                _uiState.value = UiState.Error(mensagem)
            }
        }
    }
}
