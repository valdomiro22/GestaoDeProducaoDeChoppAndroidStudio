package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    selectedRoute: String,
    onItemClick: (Route) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.70f) // O Drawer ocupará 70% da largura da tela
    ) {
        DrawerHeader()
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        // Lista de itens do Drawer
        val drawerItems = listOf(
            Route.ListaDeGradesRoute,
            Route.ListaDeProducoesRoute,
        )

        drawerItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.title) },
                selected = item.route == selectedRoute,
                onClick = { onItemClick(item) },
                icon = { item.icon?.let { Icon(it, contentDescription = null) } },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@Composable
private fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text(
            text = "Gestão de Produção",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

        // Opcional: subtítulo
        Text(
            text = "Chopp",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
        )
    }
}