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

    private val _uiState = MutableStateFlow<UiState<Map<String, QuantidadeHorariaEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun carregarDadosDaProducao(producaoId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = getQtHorariaDaProducao(producaoId = producaoId)

            result.onSuccess { lista ->
                // 1. Agrupamos a lista por horário: Map<String, List<QuantidadeHorariaEntity>>
                val mapaAgrupado = lista.groupBy { it.horarioReferente }

                // 2. Transformamos os valores (listas) no somatório das quantidades
                val mapaSomado = mapaAgrupado.mapValues { entry ->
                    val listaDoHorario = entry.value

                    // Pegamos o primeiro item como referência para manter os IDs e outros campos
                    // mas atualizamos a quantidade com a soma de todos os itens daquele horário
                    listaDoHorario.first().copy(
                        quantidade = listaDoHorario.sumOf { it.quantidade }
                    )
                }

                _uiState.value = UiState.Success(mapaSomado)
            }.onFailure {
                val mensagem = it.message ?: "Erro ao carregar quantidades"
                _uiState.value = UiState.Error(mensagem)
            }
        }
    }
}
