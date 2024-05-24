package com.luanafernandes.catapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.luanafernandes.catapp.data.local.CatBreedDatabase
import com.luanafernandes.catapp.data.local.CatBreedsRemoteKeysEntity
import com.luanafernandes.catapp.data.mappers.toCatBreedEntity
import com.luanafernandes.catapp.data.remote.CatApi
import com.luanafernandes.catapp.domain.model.CatBreed
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator @Inject constructor(
    private val catApi: CatApi,
    private val catDatabase: CatBreedDatabase
) : RemoteMediator<Int, CatBreed>() {

    private val catDao = catDatabase.catBreedsDao()
    private val catRemoteKeysDao = catDatabase.catBreedsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreed>
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

            val response = catApi.getCats(page = currentPage, limit = 15)
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
                catDao.insertAll(response.map { it.toCatBreedEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CatBreed>
    ): CatBreedsRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                catRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CatBreed>
    ): CatBreedsRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                catRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CatBreed>
    ): CatBreedsRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                catRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

}

