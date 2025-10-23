package com.calyrsoft.ucbp1.features.profile.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.calyrsoft.ucbp1.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val state = profileViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.showProfile()
    }

    when (val st = state.value) {
        is ProfileViewModel.ProfileUiState.Error -> Text(st.message)
        ProfileViewModel.ProfileUiState.Init -> Text("")
        ProfileViewModel.ProfileUiState.Loading -> CircularProgressIndicator()
        is ProfileViewModel.ProfileUiState.Success -> {
            val p = st.profile
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = p.pathUrl.value,
                    contentDescription = "Foto de perfil de ${p.name.value}",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(8.dp))
                Text(p.name.value, style = MaterialTheme.typography.titleMedium)
                Text(p.email.value, style = MaterialTheme.typography.bodyMedium)
                Text(p.cellphone.value, style = MaterialTheme.typography.bodyMedium)
                Text(
                    p.summary.value,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )


            }
        }
    }
}
