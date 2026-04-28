package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util

import android.util.Log
import java.text.DecimalFormat

object StringUtils {

    private val formatador = DecimalFormat("#.#")

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

    fun calcularVolumeNecessario(quantidade: Int, vlBarril: Int): String {
        val volume = quantidade * vlBarril / 100
        val volumeFormatado = formatador.format(volume)
        return volumeFormatado
    }
}
