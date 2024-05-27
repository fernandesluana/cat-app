package com.luanafernandes.catapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.luanafernandes.catapp.R
import com.luanafernandes.catapp.presentation.SharedViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    catId: String
) {
    val viewModel: SharedViewModel = hiltViewModel()
    val catBreed by viewModel.catBreed.observeAsState()

    LaunchedEffect(catId) {
        viewModel.fetchCatBreedById(catId)
    }

    catBreed?.let { cat ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Cat name: ${cat.name}")
            Text(text = "Cat description: ${cat.description}")
            Text(text = "Cat temperament: ${cat.temperament}")
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = cat.imageUrl)
                        .apply {
                            placeholder(R.drawable.catplaceholder)
                            error(R.drawable.catplaceholder)
                            crossfade(true)
                        }
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .padding(16.dp)
            )
        }
    }
}