package com.luanafernandes.catapp.domain.repository

import androidx.paging.PagingData
import com.luanafernandes.catapp.data.local.entities.CatBreedEntity
import com.luanafernandes.catapp.domain.model.CatBreed
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    fun getAllCats(): Flow<PagingData<CatBreedEntity>>
    suspend fun getCatById(id: String): CatBreed?
    fun searchCatBreeds(query: String): Flow<PagingData<CatBreedEntity>>
    suspend fun toggleFavorite(catId: String, isFavorite: Boolean)
    fun getFavoriteCats(): Flow<List<CatBreed>>
    suspend fun isFavorite(catId: String): Boolean
    fun getAverageLifespan(): Flow<Int>

}