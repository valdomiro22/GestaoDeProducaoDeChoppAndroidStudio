package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.atualizargrade

import java.time.LocalDate

data class AtualizarGradeState(
    val numero: String = "",
    val data: LocalDate? = null,
    val descartavel: Boolean = false,

    val erroNumero: String? = null,
    val erroData: String? = null,
    val erro: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)