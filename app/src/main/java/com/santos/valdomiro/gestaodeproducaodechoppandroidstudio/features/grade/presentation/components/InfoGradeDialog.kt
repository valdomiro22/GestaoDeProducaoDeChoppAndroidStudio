package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.components

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.LinhaChaveValorInfo
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.core.utils.DataHoraUtils
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.core.utils.DataParaDatePicker.toEpochMillis
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.StringUtils

@Composable
fun InfoGradeDialog(
    grade: GradeEntity,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            val data = DataHoraUtils.formatarData(grade.data.toString())

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LinhaChaveValorInfo(chave = "Numero", valor = grade.numero.toString())
                LinhaChaveValorInfo(chave = "Numero", valor = data ?: "")

            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = { }
    )
}