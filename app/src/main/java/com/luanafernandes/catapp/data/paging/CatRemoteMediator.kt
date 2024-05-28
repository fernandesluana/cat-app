package com.luanafernandes.catapp.data.paging

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.luanafernandes.catapp.data.local.CatBreedDatabase
import com.luanafernandes.catapp.data.local.entities.CatBreedEntity
import com.luanafernandes.catapp.data.local.entities.CatBreedsRemoteKeysEntity
import com.luanafernandes.catapp.data.mappers.toCatBreedEntity
import com.luanafernandes.catapp.data.remote.CatApi
import com.luanafernandes.catapp.data.remote.CatImageInfoDto
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator(
    private val catApi: CatApi,
    private val catDatabase: CatBreedDatabase
) : RemoteMediator<Int, CatBreedEntity>() {

    private val catDao = catDatabase.catBreedsDao()
    private val catRemoteKeysDao = catDatabase.catBreedsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreedEntity>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = catApi.getCats(page = currentPage, limit = 12)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            catDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    catDao.deleteAll()
                    catRemoteKeysDao.deleteAll()
                }
                val keys = response.map { catBreed ->
                    CatBreedsRemoteKeysEntity(
                        id = catBreed.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                catRemoteKeysDao.insertAll(keys)

                val imageUrls = response.associate { catBreed ->
                    val images: List<CatImageInfoDto> = catApi.getCatImage(catBreed.id)
                    val imageUrl = images.firstOrNull()?.url
                    catBreed.id to imageUrl
                }
                val catBreedsWithImages = response.map { catBreed ->
                    catBreed.toCatBreedEntity(imageUrl = imageUrls[catBreed.id] ?: "")
                }
                catDao.insertAll(catBreedsWithImages)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Log.e("CatRemoteMediator", "Network error: ${e.message}", e)
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("CatRemoteMediator", "HTTP error: ${e.message}", e)
            return MediatorResult.Error(e)
        } catch (e: SQLiteException) {
            Log.e("CatRemoteMediator", "Database error: ${e.message}", e)
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.e("CatRemoteMediator", "Unexpected error: ${e.message}", e)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CatBreedEntity>
    ): CatBreedsRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                catRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CatBreedEntity>
    ): CatBreedsRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                catRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CatBreedEntity>
    ): CatBreedsRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                catRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

}

