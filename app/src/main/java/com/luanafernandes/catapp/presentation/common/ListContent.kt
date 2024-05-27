package com.luanafernandes.catapp.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.luanafernandes.catapp.R
import com.luanafernandes.catapp.domain.model.CatBreed
import com.luanafernandes.catapp.presentation.SharedViewModel

@Composable
fun ListContent(
    items: LazyPagingItems<CatBreed>,
    navController: NavController,
    viewModel: SharedViewModel,
) {

    LazyVerticalGrid(
        //modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            bottom = 60.dp
        )
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id },
            contentType = items.itemContentType { "catBreedItem" }
        ) { index ->
            val catBreed = items[index]
            catBreed?.let { CatBreedItem(catBreed = it, navController, viewModel) }
        }
    }
}

@Composable
fun CatBreedItem(
    catBreed: CatBreed,
    navController: NavController,
    viewModel: SharedViewModel
) {
    val isFavorite = remember { mutableStateOf(false) }

    // Fetch the initial favorite state
    LaunchedEffect(key1 = catBreed.id) {
        isFavorite.value = viewModel.isFavorite(catBreed.id)
    }

    val imagePainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = catBreed.imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                placeholder(R.drawable.catplaceholder)
                scale(coil.size.Scale.FILL)
                crossfade(true)
                error(R.drawable.catplaceholder)

            }).build()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navController.navigate(route = "details/${catBreed.id}")

            }
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = MaterialTheme.shapes.extraSmall,
        border = BorderStroke(4.dp, Color.Black)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            )
        {

                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = imagePainter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 200f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                contentAlignment = Alignment.BottomCenter
            )
            {
                Text(
                    text = catBreed.name,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            )
            {
                IconToggleButton(
                    checked = isFavorite.value,
                    onCheckedChange = {
                        val newFavoriteState = !isFavorite.value
                        viewModel.toggleFavorite(catBreed.id, newFavoriteState)
                        isFavorite.value = newFavoriteState
                }) {
                    Icon(
                        imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "",
                        tint = Color.Red)
                }

            }

        }
    }
}


