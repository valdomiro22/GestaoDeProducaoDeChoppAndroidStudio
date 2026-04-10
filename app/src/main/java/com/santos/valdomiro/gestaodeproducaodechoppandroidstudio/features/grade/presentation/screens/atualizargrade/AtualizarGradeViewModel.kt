package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.atualizargrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase.GetOneGradeUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase.UpdateGradeUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AtualizarGradeViewModel @Inject constructor(
    private val updateGrade: UpdateGradeUseCase,
    private val getGrade: GetOneGradeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AtualizarGradeState())
    val uiState = _uiState.asStateFlow()

    private var gradeIdAtual: String? = null
    fun buscarGrade(gradeId: String) {
        gradeIdAtual = gradeId

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            getGrade(gradeId).fold(
                onSuccess = { grade ->
                    _uiState.update {
                        it.copy(
                            numero = grade.numero.toString(),
                            data = grade.data,
                            isLoading = false,
                            erro = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, erro = error.message ?: "Erro ao buscar grade")
                    }
                }
            )
        }
    }

    fun onNumeroChanged(value: String) {
        val filtered = value.filter { it.isDigit() }
        _uiState.value = _uiState.value.copy(numero = filtered, erroNumero = null)
    }

    fun onDataChanged(value: LocalDate?) {
        _uiState.update { it.copy(data = value, erroData = null) }
    }

    fun update() {
        val currenteState = _uiState.value
        val id = gradeIdAtual ?: return

        if (!validar(currenteState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            val numeroInt = currenteState.numero.toIntOrNull()
                ?: run {
                    _uiState.update {
                        it.copy(isLoading = false, erroNumero = "Número da grade inválido")
                    }
                    return@launch
                }

            if (numeroInt <= 0) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erroNumero = "Número da grade deve ser maior do que zero"
                    )
                }
                return@launch
            }

            val grade = GradeEntity(
                id = id,
                numero = numeroInt,
                data = currenteState.data!!,
            )

            updateGrade(
                grade = grade, id = id
            ).onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, erro = error.toUserMessage()) }
            }
        }
    }

    private fun validar(state: AtualizarGradeState): Boolean {
        var isValid = true
        var newState = state

        if (state.numero.isBlank()) {
            isValid = false
            newState = newState.copy(erroNumero = "Digite o numero da grade")
        }

        if (state.data == null) {
            newState = newState.copy(erroData = "Selecione a data")
            isValid = false
        }

        _uiState.update { newState }
        return isValid
    }
}