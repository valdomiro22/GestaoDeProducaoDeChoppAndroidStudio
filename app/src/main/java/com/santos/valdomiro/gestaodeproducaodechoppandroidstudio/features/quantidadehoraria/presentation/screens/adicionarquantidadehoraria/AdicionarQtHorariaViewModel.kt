package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.presentation.screens.adicionarquantidadehoraria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.Turno
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.usecase.InserQtHorariaUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdicionarQtHorariaViewModel @Inject constructor(
    private val insertQtHorariaUseCase: InserQtHorariaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdicionarQtHorariaState())
    val uiState = _uiState.asStateFlow()

    fun onQuantidadeChanged(value: String) {
        val filtrado = value.filter { it.isDigit() }
        _uiState.update { it.copy(quantidade = filtrado, erroQuantidade = null) }
    }

    fun inserir() {
        val currentState = _uiState.value

        if (!validar(currentState)) return

        viewModelScope.launch {
            val validadeInt = currentState.quantidade.toIntOrNull()
                ?: run {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroQuantidade = "Quantidade inválida"
                        )
                    }
                    return@launch
                }

            if (validadeInt == 0) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erroQuantidade = "Quantidade deve ser diferente de zero"
                    )
                }
                return@launch
            }

            val quantidade = QuantidadeHorariaEntity(
                turnoId = Turno.TURNO_A.id,  // TODO - Tornar dinâmico
                producaoId = "8f05d9b0-4362-4b01-85a9-19cd22f756d7", // TODO - Tornar dinâmico
//                producaoId = currentState.producaoId!!,
                horarioReferente = 800, // TODO - Tornar dinâmico
                quantidade = validadeInt
            )

            val result = insertQtHorariaUseCase(quantidade)
            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, erro = error.toUserMessage()) }
            }
        }
    }

    fun validar(state: AdicionarQtHorariaState): Boolean {
        var isValid = true
        var newState = state

        if (state.quantidade.isBlank()) {
            isValid = false
            newState = newState.copy(erroQuantidade = "Digite a quantidade")
        }

        _uiState.update { newState }
        return isValid;
    }
}