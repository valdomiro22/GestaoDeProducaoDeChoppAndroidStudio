package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.presentation.screens.atualizarproducao

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.StatusProducao
import java.time.LocalDate

data class AtualizarProducaoState(
    val barrilId: String? = null,
    val barrilNome: String? = null,
    val produtoId: String? = null,
    val produtoNome: String? = null,
    val gradeId: String? = null,
    val quantidade: String = "",
    val dataCriacao: LocalDate? = null,
    val status: StatusProducao? = null,
    val quantidadeProduzida: Int? = 0,

    val erroQuantidade: String? = null,
    val erroBarril: String? = null,
    val erroProduto: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)