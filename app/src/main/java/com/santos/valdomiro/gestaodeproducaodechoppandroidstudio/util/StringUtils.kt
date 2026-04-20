package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util

import android.util.Log

object StringUtils {

    fun estrairHorarioReferente(horario: String): Int {
        Log.d(TAG, "estrairHorarioReferente: Horario recebido: $horario")
        val horarioSemPontos = horario.replace(
            ":",
            newValue = "",
            ignoreCase = true
        )

        val horarioInt = horarioSemPontos.toIntOrNull() ?: 0
        return horarioInt
    }
}
