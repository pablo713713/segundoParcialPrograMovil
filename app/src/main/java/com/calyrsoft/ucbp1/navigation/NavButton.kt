package com.calyrsoft.ucbp1.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NavButton(
    navController: NavController,
    to: Screen,
    text: String
) {
    Button(onClick = { navController.navigate(to.route) }) {
        Text(text)
    }
}
