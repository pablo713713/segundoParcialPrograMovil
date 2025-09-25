package com.calyrsoft.ucbp1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.calyrsoft.ucbp1.features.book.presentation.BookScreen
import com.calyrsoft.ucbp1.features.cardexample.presentation.CardScreen
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarScreen
import com.calyrsoft.ucbp1.features.github.presentation.GithubScreen
import com.calyrsoft.ucbp1.features.movie.presentation.PopularMoviesScreen
import com.calyrsoft.ucbp1.features.profile.application.ProfileScreen

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PopularMovies.route
    ) {
        composable(Screen.Github.route) {
            GithubScreen(modifier = Modifier)
        }
        composable(Screen.Home.route) {

        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.CardExamples.route) { CardScreen() }

        composable(Screen.Dollar.route) {
            DollarScreen()
        }

        composable(Screen.Book.route) {
            BookScreen()
        }

        composable(Screen.PopularMovies.route) { PopularMoviesScreen() }
    }
}