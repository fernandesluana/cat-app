package com.luanafernandes.catapp.presentation.navigation


sealed class Screen (
    val route: String,
    val title: String,
){
    data object Home: Screen(
        route = "home",
        title = "Home"
    )
    data object Favorites: Screen(
        route = "favorites",
        title = "Favorites"
    )
    data object Details: Screen(
        route = "details/{catId}",
        title = "Details"
    )
}