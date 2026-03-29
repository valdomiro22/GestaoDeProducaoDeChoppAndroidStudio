package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro.CadastroScreen
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.ui.theme.GestaoDeProducaoDeChoppAndroidStudioTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestaoDeProducaoDeChoppAndroidStudioTheme {
                    val destinoInicial = viewModel.startDestination
                MainAppScreen(destinoInicial)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    CadastroScreen()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GestaoDeProducaoDeChoppAndroidStudioTheme {
        Greeting("Android")
    }
}