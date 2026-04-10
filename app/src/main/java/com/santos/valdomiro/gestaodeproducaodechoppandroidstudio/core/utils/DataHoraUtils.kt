import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object DataHoraUtils {

    // Função auxiliar privada para imitar a flexibilidade do DateTime.parse() do Dart,
    // que aceita strings com ou sem horário e com ou sem a letra 'T'.
    private fun parseToLocalDateTimeOrDate(data: String): LocalDate {
        return try {
            if (data.contains("T") || data.contains(" ")) {
                LocalDateTime.parse(data.replace(" ", "T")).toLocalDate()
            } else {
                LocalDate.parse(data) // Tenta parsear apenas como data (yyyy-MM-dd)
            }
        } catch (e: Exception) {
            LocalDate.now() // Fallback de segurança, ajustável conforme sua regra de negócios
        }
    }

    private fun parseToLocalTime(data: String): LocalTime {
        return if (data.contains("T") || data.contains(" ")) {
            LocalDateTime.parse(data.replace(" ", "T")).toLocalTime()
        } else {
            LocalTime.parse(data)
        }
    }

    fun formatarData(data: String?): String? {
        if (data == null) return null

        val dataConvertida = parseToLocalDateTimeOrDate(data)
        val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
        return dataConvertida.format(formatador)
    }

    fun formatarDataComPontos(data: String?): String? {
        if (data == null) return null

        val dataConvertida = parseToLocalDateTimeOrDate(data)
        val formatador = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("pt", "BR"))
        return dataConvertida.format(formatador)
    }

    fun stringParaDateTime(data: String): LocalDate? {
        return try {
            val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            LocalDate.parse(data, formatador)
        } catch (e: DateTimeParseException) {
            println("Erro ao formatar data: ${e.message}") // Substitui o dev.log do Dart
            null
        }
    }

    fun formatarDataBarrasParaPontos(data: String): String {
        return data.replace("/", ".")
    }

    // No Kotlin, os Locales já são nativos na JVM/Android, portanto
    // não precisamos de "await" ou coroutines (suspend) para formatar.
    fun formatarHora(data: String): String {
        val horaConvertida = parseToLocalTime(data)
        val formatador = DateTimeFormatter.ofPattern("HH:mm:ss", Locale("pt", "BR"))
        return horaConvertida.format(formatador)
    }

    fun formatarHoraSincrona(data: String): String {
        return formatarHora(data) // Reutilizamos a função acima, já que ambas são síncronas agora
    }

    fun capitalize(text: String): String {
        if (text.isEmpty()) {
            return ""
        }
        return text[0].uppercaseChar() + text.substring(1).lowercase()
    }

    fun hojeSemHorario(): LocalDate {
        // LocalDate já representa exclusivamente uma data (ano, mês, dia) sem o horário
        return LocalDate.now()
    }

    fun agoraSemData(): LocalTime {
        // LocalTime representa exclusivamente um horário, sem atrelar a um dia
        return LocalTime.now()
    }

    fun agoraSemSegundos(): LocalTime {
        val agora = LocalTime.now()
        // Cria um horário ignorando segundos e nanosegundos
        return LocalTime.of(agora.hour, agora.minute)
    }
}