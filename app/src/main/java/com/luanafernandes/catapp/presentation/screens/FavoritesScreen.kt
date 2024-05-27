package com.luanafernandes.catapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luanafernandes.catapp.presentation.SharedViewModel
import com.luanafernandes.catapp.presentation.common.CatBreedItem

@Composable
fun FavoritesScreen(
    navController: NavController
){
    val viewModel: SharedViewModel = hiltViewModel()
    val favoriteCats by viewModel.favoriteCats.observeAsState(emptyList())

    val averageLifespan by viewModel.averageLifespan.collectAsState(initial = 0)


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Your Favorite Cats",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 25.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Average Lifespan of your favorite Breeds: $averageLifespan years")
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 12.dp,
                bottom = 60.dp
            )
        ) {
            items(favoriteCats) { catBreed ->
                CatBreedItem(
                    catBreed = catBreed,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}