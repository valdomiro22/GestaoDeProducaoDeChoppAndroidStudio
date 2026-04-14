package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.homescreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.Turno
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val turnoAtual by viewModel.turnoSelecionado.collectAsState()
    var menuExpandido by remember { mutableStateOf(false) }  // Para o controle do DropdownMenu

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Itaipava 50L",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },

                actions = {
                    IconButton(onClick = { menuExpandido = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Mais opções",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Opção 1") },
                            onClick = {
                                menuExpandido = false
                                Toast.makeText(context, "Opção 1", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Opção 2") },
                            onClick = {
                                menuExpandido = false
                                Toast.makeText(context, "Opção 2", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                },

                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E5FDB),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            CabecalhoComponent("Itaipava 50L")
//            Spacer(modifier = Modifier.height(8.dp))

            // Seção de status da produção
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardProducaoComponent(
                    titulo = "Programado",
                    backGround = Color(0xFF1E5FDB),
                    quantidade = "874"
                )
                CardProducaoComponent(
                    backGround = Color(0xFF15AD1C),
                    titulo = "Produzido",
                    quantidade = "354"
                )
                CardProducaoComponent(
                    backGround = Color(0xFFE52828),
                    titulo = "Pendente",
                    quantidade = "458"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Seção de seleção de turno
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Turno.entries.forEach { turno ->
                    val selecionado = turno == turnoAtual
                    val corBase = if (selecionado) Color(0xFF2563EB) else Color(0xFFF0F0F0)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(corBase)
                            .clickable { viewModel.alterarTurno(turno) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = turno.label,
                            color = if (selecionado) Color.White else Color.DarkGray,
                            fontWeight = if (selecionado) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Seção de horários do turno selecionado
            val listaDeHorarios = turnoAtual.horarios.values.toList()
            LazyVerticalGrid(
                columns = GridCells.Fixed(4), // 4 colunas costumam ler melhor que 5 em telas menores
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 230.dp), // heightIn é melhor que height fixo
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(listaDeHorarios) { horario ->
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF8F9FA))
                            .border(1.dp, Color(0xFFE9ECEF), RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = horario,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF495057)
                        )
                        Text(
                            text = "100", // Unidade ou quantidade sutil
                            fontSize = 14.sp,
                            color = Color(0xFF0BA884),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Informações para o final de produção
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LinhaChaveValor(
                    chave = "Volume do Barril:",
                    valor = "50L",
                    modifier = Modifier.fillMaxWidth()
                )

                LinhaChaveValor(
                    chave = "Volume necessário:",
                    valor = "458 hl",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp))
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VERIFIQUE O BUFFER",
                        color = Color(0xFFB71C1C),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomeScreen()
}