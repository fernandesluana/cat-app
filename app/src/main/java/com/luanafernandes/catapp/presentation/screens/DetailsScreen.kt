package com.luanafernandes.catapp.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val isFavorite = remember { mutableStateOf(false) }

    LaunchedEffect(catId) {
        viewModel.fetchCatBreedById(catId)
    }

    LaunchedEffect(key1 = catBreed?.id) {
        catBreed?.id?.let { id ->
            isFavorite.value = viewModel.isFavorite(id)
        }
    }

    catBreed?.let { cat ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(4.dp, Color.Black)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
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
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        IconToggleButton(
                            checked = isFavorite.value,
                            onCheckedChange = {
                                val newFavoriteState = !isFavorite.value
                                viewModel.toggleFavorite(cat.id, newFavoriteState)
                                isFavorite.value = newFavoriteState
                            }) {
                            Icon(
                                imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = cat.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = cat.origin
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = cat.temperament,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cat.description,
                textAlign = TextAlign.Center
            )

        }
    }
}