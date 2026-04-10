package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.screens.atualizargrade

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.CarregandoComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.components.ErroComponent
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.presentation.components.AppDatePicker
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.common.components.CustomOutlinedTextField
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtualizarGradeScreen(
    gradeId: String,
    viewModel: AtualizarGradeViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(gradeId) {
        viewModel.buscarGrade(gradeId)
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
            Toast.makeText(context, "Grade atualizada", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atualizar grade") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6450A1), // Sua cor de fundo (Ex: Roxo)
                    titleContentColor = Color.White,    // Cor padrão do título
                    navigationIconContentColor = Color.White // Cor padrão do ícone
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Numero da Grade")
            CustomOutlinedTextField(
                value = state.numero,
                onValueChange = viewModel::onNumeroChanged,
                isErro = state.erroNumero!= null,
                inputType = KeyboardType.Text,
                placeholder = "Numero"
            )
            if (state.erroNumero != null) ErroComponent(state.erroNumero!!)
            Spacer(modifier = Modifier.height(8.dp))

            AppDatePicker(
                selectedDate = state.data,
                onDateSelected = viewModel::onDataChanged,
                label = "Data de Produção"
            )
            if (state.erroData != null) ErroComponent(state.erroData!!)
            Spacer(modifier = Modifier.height(24.dp))

            ButtomFillMaxWidth(
                nome = "Atualizar",
                onClick = viewModel::update,
            )

            if (state.isLoading) CarregandoComponent(cor = Color.Magenta)
        }

    }

}