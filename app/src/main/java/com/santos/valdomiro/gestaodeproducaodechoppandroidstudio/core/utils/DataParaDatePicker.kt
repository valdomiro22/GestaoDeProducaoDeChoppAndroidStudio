import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DataParaDatePicker {

    // LocalDate → millis (para passar pro DatePickerState)
    fun LocalDate?.toEpochMillis(): Long? {
        return this?.atStartOfDay(ZoneId.systemDefault())
            ?.toInstant()
            ?.toEpochMilli()
    }

    // millis → LocalDate (quando o usuário confirma a seleção)
    fun Long?.toLocalDate(): LocalDate? {
        return this?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
    }

    // Formatação bonita para exibir no TextField
    fun LocalDate?.formatToBrazilian(): String {
        if (this == null) return "Selecione uma data"
        return DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
            .format(this)
    }
}