package com.luanafernandes.catapp.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luanafernandes.catapp.data.local.CatBreedDatabase
import com.luanafernandes.catapp.data.local.entities.CatBreedEntity
import com.luanafernandes.catapp.data.mappers.toCatBreed
import com.luanafernandes.catapp.data.mappers.toFavoriteCatsEntity
import com.luanafernandes.catapp.data.paging.CatRemoteMediator
import com.luanafernandes.catapp.data.remote.CatApi
import com.luanafernandes.catapp.domain.model.CatBreed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CatRepository @Inject constructor(
    private val catApi: CatApi,
    private val catDatabase: CatBreedDatabase,
) {

    fun getAllCats(): Flow<PagingData<CatBreedEntity>> {
        val pagingSourceFactory = { catDatabase.catBreedsDao().getAllCatBreeds() }
        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = CatRemoteMediator(catApi, catDatabase)
        ).flow
    }

    fun searchCatBreeds(query: String): Flow<PagingData<CatBreedEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { catDatabase.catBreedsDao().getCatBreedsByName("%$query%") }
        ).flow
    }

    suspend fun getCatBreed(id: String): CatBreed {
        return catDatabase.catBreedsDao().getCatBreedById(id).toCatBreed()
    }

    suspend fun toggleFavorite(catId: String, isFavorite: Boolean) {
        val catBreed = catDatabase.catBreedsDao().getCatBreedById(catId)
        val favoriteCatEntity = catBreed.toFavoriteCatsEntity()
        if (isFavorite) {
            catDatabase.favoriteCatsDao().insertFavoriteCat(favoriteCatEntity)
        } else {
            catDatabase.favoriteCatsDao().deleteFavoriteCat(favoriteCatEntity)
        }
    }

    fun getFavoriteCats(): Flow<List<CatBreed>> {
        return catDatabase.favoriteCatsDao().getFavoriteCats()
            .map { favoriteCatsEntities ->
                favoriteCatsEntities.map { it.toCatBreed() }
            }
    }

    suspend fun isFavorite(catId: String): Boolean {
        return catDatabase.favoriteCatsDao().getFavoriteCatById(catId) != null
    }



}