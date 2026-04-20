package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity

@Composable
fun AddQtHorariaDialog(
    producao: ProducaoEntity,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Barris produzidos",
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    isError = false,
                    placeholder = { Text("Ex: 50") },
                    label = { Text("Quantidade") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Chips de incremento
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ActionChipCustom(
                        valor = 1,
                        isIncrement = true,
                        onClick = {}
                    )
                    ActionChipCustom(
                        valor = 5,
                        isIncrement = true,
                        onClick = {}
                    )
                    ActionChipCustom(
                        valor = 10,
                        isIncrement = true,
                        onClick = {}
                    )
                    ActionChipCustom(
                        valor = 20,
                        isIncrement = true,
                        onClick = {}
                    )
                }

                // Chips de decremento
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ActionChipCustom(
                        valor = 1,
                        isIncrement = false,
                        onClick = {}
                    )
                    ActionChipCustom(
                        valor = 5,
                        isIncrement = false,
                        onClick = {}
                    )
                    ActionChipCustom(
                        valor = 10,
                        isIncrement = false,
                        onClick = {}
                    )
                    ActionChipCustom(
                        valor = 20,
                        isIncrement = false,
                        onClick = {}
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}