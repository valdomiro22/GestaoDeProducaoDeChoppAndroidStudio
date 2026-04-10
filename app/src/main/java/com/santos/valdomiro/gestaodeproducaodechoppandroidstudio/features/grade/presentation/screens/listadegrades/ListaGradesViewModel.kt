package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.listadegrades

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.state.UiState
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase.DeleteGradeUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase.GetAllGradesUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaGradesViewModel @Inject constructor(
    private val getListaGradesUseCase: GetAllGradesUseCase,
    private val deleteGradeUseCase: DeleteGradeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GradeEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = getListaGradesUseCase()

            result.onSuccess { listaGrades ->
                Log.d(TAG, "getAll: Lista de grades retornada: ${listaGrades.size}")
                _uiState.value = UiState.Success(listaGrades)
            }.onFailure {
                Log.d(TAG, "getAll: Erro ao buscar lista de grades \n${it.message}")
                val mensagem = it.message ?: "Erro ao carregar lista de grades"
                _uiState.value = UiState.Error(mensagem)
            }
        }
    }

    fun deleteGrade(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            deleteGradeUseCase(id)
                .onSuccess {
                    getAll()  // recarrega a lista após deletar
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao excluir grade"
                    )
                }
        }
    }

}