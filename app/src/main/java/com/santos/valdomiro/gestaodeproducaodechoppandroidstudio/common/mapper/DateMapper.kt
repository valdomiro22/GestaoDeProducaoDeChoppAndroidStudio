package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.mapper

import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

// Converte LocalDate → Date (para salvar no Firestore)
fun LocalDate.toFirestoreDate(): Date {
    return Date.from(
        this.atStartOfDay(ZoneId.of("UTC"))
            .toInstant()
    )
}

// Converte Date (do Firestore) → LocalDate
fun Date.toLocalDate(): LocalDate {
    return this.toInstant()
        .atZone(ZoneId.of("UTC"))
        .toLocalDate()
}