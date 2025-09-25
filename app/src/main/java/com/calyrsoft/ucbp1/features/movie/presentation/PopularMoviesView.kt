package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

@Composable
fun PopularMoviesView(movies: List<MovieModel>) {
    if (movies.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Sin resultados.")
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            CardMovie(movie = movie)
        }
    }
}

@Composable
fun CardMovie(movie: MovieModel) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (movie.pathUrl != null) {
                AsyncImage(
                    model = movie.pathUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.66f), // poster típico
                    contentScale = ContentScale.Crop
                )
            } else {
                // Fallback cuando no hay poster
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.66f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sin imagen", textAlign = TextAlign.Center)
                }
            }

            Text(
                text = movie.title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                maxLines = 2
            )
        }
    }
}
