package com.calyrsoft.ucbp1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.calyrsoft.ucbp1.features.book.presentation.BookScreen
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarScreen
import com.calyrsoft.ucbp1.features.github.presentation.GithubScreen
import com.calyrsoft.ucbp1.features.movie.presentation.PopularMoviesScreen
import com.calyrsoft.ucbp1.features.profile.presentation.ProfileScreen

@Composable
fun AppNavigation( navigationViewModel: NavigationViewModel, modifier: Modifier, navController: NavHostController) {
    LaunchedEffect(Unit) {
        navigationViewModel.navigationCommand.collect { command ->
            when (command) {
                is NavigationViewModel.NavigationCommand.NavigateTo -> {
                    navController.navigate(command.route) {
                        // Configuración del back stack según sea necesario
                        when (command.options) {
                            NavigationOptions.CLEAR_BACK_STACK -> {
                                popUpTo(0) // Limpiar todo el back stack
                            }
                            NavigationOptions.REPLACE_HOME -> {
                                popUpTo(Screen.Dollar.route) { inclusive = true }
                            }
                            else -> {
                                // Navegación normal
                            }
                        }
                    }
                }
                is NavigationViewModel.NavigationCommand.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }
    }


    NavHost(
        navController = navController,
        startDestination = Screen.Dollar.route,
        modifier = modifier

    ) {
        composable(Screen.Github.route) { GithubScreen(modifier = Modifier) }

        composable(Screen.Home.route) { /* ... */ }

        composable(Screen.Profile.route) { ProfileScreen(navController) }


        composable(Screen.Dollar.route) { DollarScreen(navController) }            // ← pasa navController

        composable(Screen.Book.route) { BookScreen() }

        composable(Screen.PopularMovies.route) { PopularMoviesScreen(navController) } // ← pasa navController
    }
}
