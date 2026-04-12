package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.adicionarproducao

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.entity.BarrilEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.StatusProducao
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase.InsertProducaoUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.mappers.toUserMessage
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AdicionarProducaoViewModel @Inject constructor(
    private val inserirProducao: InsertProducaoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdicionarProducaoState())
    val uiState = _uiState.asStateFlow()

    fun onProdutoChanged(value: ProdutoEntity?) {
        Log.i(TAG, "onProdutoChanged: Produto: ${value?.nome}")
        _uiState.update {
            it.copy(
                produtoId = value?.id,
                produtoNome = value?.nome,
                erroProduto = null
            )
        }
    }

    fun onBarrilChanged(value: BarrilEntity?) {
        _uiState.update {
            it.copy(
                barrilId = value?.id,
                barrilNome = value?.nome,
                erroBarril = null
            )
        }
    }

    fun onQuantidadeChanged(value: String) {
        _uiState.update { it.copy(quantidade = value, erroQuantidade = null) }
    }

    fun inserir(gradeId: String) {
        val currentState = _uiState.value

        if (!validar(currentState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            val quantidadeInt = currentState.quantidade.toIntOrNull()
                ?: run {
                    _uiState.update {
                        it.copy(isLoading = false, erroQuantidade = "Quantidade inválida")
                    }
                    return@launch
                }

            if (quantidadeInt <= 0) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erroQuantidade = "Quantidade deve ser maior do que zero"
                    )
                }
                return@launch
            }

            val producao = ProducaoEntity(
                gradeId = gradeId,
                status = StatusProducao.NAO_CONCLUIDA,
                produtoId = currentState.produtoId!!,
                produtoNome = currentState.produtoNome!!,
                barrilId = currentState.barrilId!!,
                barrilNome = currentState.barrilNome!!,
                quantidadeProgramada = quantidadeInt,
                dataCriacao = LocalDate.now()
            )

            inserirProducao(producao)
                .onSuccess { _uiState.update { it.copy(isLoading = false, isSuccess = true) } }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, erro = error.toUserMessage()) }
                }
        }
    }

    fun validar(state: AdicionarProducaoState): Boolean {
        var isValid = true
        var newState = state

        if (state.produtoId.isNullOrEmpty()) {
            isValid = false
            newState = newState.copy(erroProduto = "Selecione um produto")
        }

        if (state.barrilId.isNullOrEmpty()) {
            isValid = false
            newState = newState.copy(erroBarril = "Selecione um barril")
        }

        if (state.quantidade.isEmpty()) {
            isValid = false
            newState = newState.copy(erroQuantidade = "Digite a quantidade")
        }

        _uiState.update { newState }
        return isValid
    }
}