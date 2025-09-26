package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calyrsoft.ucbp1.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun DollarScreen(
    navController: NavController,
    viewModelDollar: DollarViewModel = koinViewModel()
) {
    val state by viewModelDollar.uiState.collectAsState()
    val history by viewModelDollar.history.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --- Sección: valor actual ---
        when (val s = state) {
            is DollarViewModel.DollarUIState.Error -> {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(s.message)
                }
            }
            DollarViewModel.DollarUIState.Loading -> {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is DollarViewModel.DollarUIState.Success -> {
                Text(
                    text = "Tipos de cambio",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))

                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RateCard(
                            title = "Oficial",
                            value = s.data.dollarOfficial ?: "-",
                            modifier = Modifier.weight(1f)
                        )
                        RateCard(
                            title = "Paralelo",
                            value = s.data.dollarParallel ?: "-",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RateCard(
                            title = "USDT",
                            value = s.data.dollarUsdt ?: "-",
                            modifier = Modifier.weight(1f)
                        )
                        RateCard(
                            title = "USDC",
                            value = s.data.dollarUsdc ?: "-",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text(
            text = "Histórico (más nuevo → más antiguo)",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        // --- Sección: lista histórica ---
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = history,
                key = { it.id }
            ) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text("Oficial: ${item.dollarOfficial ?: "-"}")
                        Text("Paralelo: ${item.dollarParallel ?: "-"}")
                        Text("USDT: ${item.dollarUsdt ?: "-"}")
                        Text("USDC: ${item.dollarUsdc ?: "-"}")
                        Text("Fecha: ${viewModelDollar.formatDate(item.timestamp)}")
                    }

                    IconButton(onClick = { viewModelDollar.deleteDollar(item.id) }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.Delete,
                            contentDescription = "Eliminar"
                        )
                    }
                }
                Divider(Modifier.padding(top = 8.dp))
            }
        }

        // --- Botón al final para navegar a Movies ---
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { navController.navigate(Screen.PopularMovies.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Películas")
        }
    }
}

@Composable
private fun RateCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}
