package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.configuracoesdeusuario

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ButtomFillMaxWidth

@Composable
fun ConfiguracoesDeUsuarioScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtomFillMaxWidth(
            onClick = { Toast.makeText(context, "Deslogar", Toast.LENGTH_SHORT).show()},
            nome = "Deslogar"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = {},
            nome = "Alterar Nome"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = {},
            nome = "Alterar E-mail"
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtomFillMaxWidth(
            onClick = {},
            nome = "Alterar Senha"
        )
        Spacer(modifier = Modifier.height(16.dp))


        ButtomFillMaxWidth(
            onClick = {},
            nome = "Excluir Conta"
        )
    }
}