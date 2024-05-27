package com.luanafernandes.catapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luanafernandes.catapp.presentation.screens.DetailsScreen
import com.luanafernandes.catapp.presentation.screens.FavoritesScreen
import com.luanafernandes.catapp.presentation.screens.HomeScreen

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Favorites.route
        ) {
            FavoritesScreen(navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("catId") {
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            val catId = backStackEntry.arguments?.getString("catId")

            catId?.let {
                DetailsScreen(
                    catId = it, navController = navController
                )
            }

        }
    }
}