package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.simularfimproducao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.useCase.GetOneBarrilUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase.GetOneProducaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SimularFimProducaoViewModel @Inject constructor(
    private val getOneBarrilUseCase: GetOneBarrilUseCase,
    private val getOneProducaoUseCase: GetOneProducaoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SimularFimProducaoState())
    val uiState = _uiState.asStateFlow()

    fun onQtProgramadaChanged(value: String) {
        val filtered = value.filter { it.isDigit() }
        _uiState.update { it.copy(qtProgramada = filtered, erroQtProgramada = null) }
    }

    fun onQtProduzidaChanged(value: String) {
        val filtered = value.filter { it.isDigit() }
        _uiState.update { it.copy(qtProduzida = value, erroQtProduzida = null) }
    }

    fun onNivelMaxChanged(value: String) {
        val filtered = value.filter { it.isDigit() }
        _uiState.update { it.copy(nivelMaxTanque = filtered, erroNivelMaxTanque = null) }
    }

    fun carregarDadosIniciais(producaoId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            // 1. Busca a Produção
            getOneProducaoUseCase(id = producaoId).fold(
                onSuccess = { producao ->
                    // 2. Com a produção em mãos, busca o barril
                    getOneBarrilUseCase(id = producao.barrilId).fold(
                        onSuccess = { barril ->
                            // 3. Sucesso total: Atualiza o estado com ambos
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    producao = producao,
                                    barril = barril,
                                    qtProgramada = producao.quantidadeProgramada.toString() // Opcional: pré-preenche
                                )
                            }
                        },
                        onFailure = { e ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    erro = "Erro ao buscar barril: ${e.message}"
                                )
                            }
                        }
                    )
                },
                onFailure = { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erro = "Erro ao buscar produção: ${e.message}"
                        )
                    }
                }
            )
        }
    }

    fun simular() {
        val currentState = _uiState.value

        // Verificamos se o barril foi carregado para ter o volume
        val volumeBarril = currentState.barril?.volume ?: 0

        if (!validar(currentState)) return
        if (volumeBarril == 0) {
            _uiState.update { it.copy(erro = "Dados do barril não disponíveis") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            val qtProgramada = currentState.qtProgramada.toIntOrNull() ?: 0
            val qtProduzida = currentState.qtProduzida.toIntOrNull() ?: 0

            if (qtProgramada <= 0 || qtProduzida <= 0) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val qtFalta = (qtProgramada - qtProduzida).coerceAtLeast(0)
            val vlCalculado = (qtFalta.toDouble() * volumeBarril) / 100.0

            _uiState.update {
                it.copy(
                    vlNecessario = String.format("%.2f", vlCalculado),
                    isLoading = false
                )
            }
        }
    }

    private fun validar(state: SimularFimProducaoState): Boolean {
        var isValid = true
        var newState = state

        if (state.qtProgramada.isBlank()) {
            isValid = false
            newState = newState.copy(erroQtProgramada = "Digite a quantidade programada")
        }

        if (state.qtProduzida.isEmpty()) {
            isValid = false
            newState = newState.copy(erroQtProduzida = "Digite a quantidade produzida")
        }

        if (state.nivelMaxTanque.isEmpty()) {
            isValid = false
            newState = newState.copy(erroNivelMaxTanque = "Digite o nível maximo do buffer")
        }

        _uiState.update { newState }
        return isValid
    }
}