package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun DollarScreen(viewModelDollar: DollarViewModel = koinViewModel()) {
    val state by viewModelDollar.uiState.collectAsState()
    val history by viewModelDollar.history.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --- Sección: valor actual (tu lógica original) ---
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (val s = state) {
                is DollarViewModel.DollarUIState.Error -> Text(s.message)
                DollarViewModel.DollarUIState.Loading -> CircularProgressIndicator()
                is DollarViewModel.DollarUIState.Success -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Dólar oficial: ${s.data.dollarOfficial ?: "-"}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Dólar paralelo: ${s.data.dollarParallel ?: "-"}",
                            style = MaterialTheme.typography.titleMedium
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
                        Text("Fecha: ${viewModelDollar.formatDate(item.timestamp)}")
                    }

                    // --- Ícono eliminar ---
                    androidx.compose.material3.IconButton(
                        onClick = { viewModelDollar.deleteDollar(item.id) }
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.Delete,
                            contentDescription = "Eliminar"
                        )
                    }
                }
                androidx.compose.material3.Divider(Modifier.padding(top = 8.dp))
            }
        }
    }
}
