package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.OutlinedTextFieldSenha

@Composable
fun CadastroScreen(modifier: Modifier = Modifier) {


    val context = LocalContext.current

    val scrollState = rememberScrollState()  // Para ScrollView

    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmarSenha by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Cadastro",
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = modifier
                .verticalScroll(scrollState)

        ) {
            Text("Nome")
            OutlinedTextField(
                value = nome,
                maxLines = 1,
                onValueChange = { nome = it },
                placeholder = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text("Sobrenome")
            OutlinedTextField(
                value = sobrenome,
                maxLines = 1,
                onValueChange = { sobrenome = it },
                placeholder = { Text("Sobrenome") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text("E-mail")
            OutlinedTextField(
                value = email,
                maxLines = 1,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text("Senha")
            OutlinedTextFieldSenha(
                value = senha,
                onValueChange = { senha = it },
                placeholder = "Senha",
                isSenhaVisivel = mostrarSenha,
                onVisibilityChange = { mostrarSenha = !mostrarSenha }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text("Confirmar senha")
            OutlinedTextFieldSenha(
                value = confirmarSenha,
                onValueChange = { confirmarSenha = it },
                placeholder = "Confirmar senha",
                isSenhaVisivel = mostrarConfirmarSenha,
                onVisibilityChange = { mostrarConfirmarSenha = !mostrarConfirmarSenha }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                Toast.makeText(context, "Cadastrar", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Cadastrar")
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Já possui uma conta?")
            TextButton(
                onClick = {
                    Toast.makeText(context, "Logar", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Logar")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CadastroScreen()
}