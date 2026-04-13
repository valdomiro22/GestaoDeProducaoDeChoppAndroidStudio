package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.Turno
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelecionarTurno(
    turnoAtual: Turno,
    onTurnoChange: (Turno) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
//    val opcoes = listOf("Turno A", "Turno B", "Turno C")
//    var itemSelecionado by remember { mutableStateOf(opcoes[0]) }

    // O container principal que gerencia o estado do Menu
    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido },
        modifier = Modifier.fillMaxWidth()
    ) {
        // O campo de texto que serve como "botão"
        OutlinedTextField(
            value = turnoAtual.label,
            onValueChange = {},
            readOnly = true, // Impede que o usuário digite
            label = { Text("Selecione o Turno") },
            trailingIcon = {
                // O ícone que gira automaticamente quando expande
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .fillMaxWidth()
        )

        // O menu que aparece ao clicar
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            Turno.entries.forEach { turno ->
                DropdownMenuItem(
                    text = { Text(turno.label) },
                    onClick = {
                        onTurnoChange(turno)
                        Log.d(TAG, "SelecionarTurno: ${turno.label}")
                        expandido = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}