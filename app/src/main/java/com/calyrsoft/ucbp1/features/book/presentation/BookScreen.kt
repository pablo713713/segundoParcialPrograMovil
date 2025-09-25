package com.calyrsoft.ucbp1.features.book.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder

@Composable
fun BookScreen(
    modifier: Modifier = Modifier,
    vm: BookViewModel = koinViewModel()
) {
    var query by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()
    val liked by vm.liked.collectAsState() // 👈 lista local de “me gusta”

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Buscador
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Nombre del libro") },
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { vm.searchBooks(query) },
                enabled = query.isNotBlank()
            ) { Text("Buscar") }
        }

        // Resultados de búsqueda
        when (val st = state) {
            is BookViewModel.BookStateUI.Init -> Text("Ingresa un título y presiona Buscar.")
            is BookViewModel.BookStateUI.Loading -> CircularProgressIndicator()
            is BookViewModel.BookStateUI.Error -> Text("Error: ${st.message}")
            is BookViewModel.BookStateUI.Success -> {
                val results = st.books
                if (results.isEmpty()) {
                    Text("Sin resultados.")
                } else {
                    Text("Resultados: ${results.size}")
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(results) { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(Modifier.weight(1f)) {
                                    val authors = item.authors.joinToString(", ").ifBlank { "-" }
                                    val year = item.year?.toString() ?: "-"
                                    Text("Título: ${item.title}")
                                    Text("Autor(es): $authors")
                                    Text("Año: $year")
                                }

                                // Estado visual de “me gusta”: lleno si ya está guardado
                                val isLiked = liked.any {
                                    it.title == item.title &&
                                            it.year == item.year &&
                                            it.authors == item.authors
                                }

                                IconButton(onClick = { vm.likeBook(item) }) {
                                    Icon(
                                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Me gusta"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Lista local de “me gusta”
        Spacer(Modifier.height(8.dp))
        Text("Mis libros guardados")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(liked) { fav ->
                Column(Modifier.fillMaxWidth()) {
                    val authors = fav.authors.joinToString(", ").ifBlank { "-" }
                    val year = fav.year?.toString() ?: "-"
                    Text("Título: ${fav.title}")
                    Text("Autor(es): $authors")
                    Text("Año: $year")
                }
            }
        }
    }
}
