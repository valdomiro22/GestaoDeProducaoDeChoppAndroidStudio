package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.LocalNavController
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentRoute: String?,
    onNavigate: (Route) -> Unit,
    onCloseDrawer: () -> Unit,
    onLogoutClick: (() -> Unit)? = null,
    drawerViewModel: DrawerViewModel = hiltViewModel()
) {

    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.70f) ,
        windowInsets = WindowInsets(0)
    ) {
        DrawerHeader()
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        // Lista de itens do Drawer
        val drawerItems = listOf(
            Route.ListaDeGradesRoute,
            Route.CalcualrTempoParadaRoute,
        )

        drawerItems.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationDrawerItem(
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    onNavigate(item)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                icon = item.icon?.let {
                    {
                        androidx.compose.material3.Icon(
                            it,
                            contentDescription = null,
                            tint = if (isSelected) Color(0xFF00BDA7)
                            else Color(0xFF1300BD) // Cor personalizada para os ícones do Drawer
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        NavigationDrawerItem(
            label = { Text("Deslogar") },
            selected = false,
            onClick = { drawerViewModel.deslogar() },
            icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp, vertical = 28.dp)
    ) {
        Text(
            text = "Gestão de Produção",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Chopp",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
        )
    }
}