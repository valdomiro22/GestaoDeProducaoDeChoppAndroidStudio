package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.collection.LLRBNode
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.entity.BarrilEntity

@Composable
fun ItemListaBarril(
    barril: BarrilEntity
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6450A1)   // sua cor desejada
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Tipo:")
                Text(
                    barril.nome,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Volume: ${barril.volume} litros")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Descartável")
       }

    }


}