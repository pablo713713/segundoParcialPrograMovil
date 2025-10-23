package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calyrsoft.ucbp1.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopularMoviesScreen(
    navController: NavController,
    popularMoviesViewModel: PopularMoviesViewModel = koinViewModel()
) {
    val state by popularMoviesViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        popularMoviesViewModel.fetchPopularMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when (val s = state) {
            is PopularMoviesViewModel.UiState.Error -> {
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(s.message)
                }
            }
            is PopularMoviesViewModel.UiState.Loading -> {
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is PopularMoviesViewModel.UiState.Success -> {
                // La grilla ocupa el espacio disponible
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    PopularMoviesView(movies = s.movies)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

    }
}
