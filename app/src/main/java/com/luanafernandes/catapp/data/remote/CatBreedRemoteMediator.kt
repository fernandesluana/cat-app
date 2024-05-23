package com.luanafernandes.catapp.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.luanafernandes.catapp.data.local.CatBreedDatabase
import com.luanafernandes.catapp.data.local.CatBreedEntity
import com.luanafernandes.catapp.data.mappers.toCatBreedEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class CatBreedRemoteMediator(
    private val catDb: CatBreedDatabase,
    private val catApi: CatApi
): RemoteMediator<Int, CatBreedEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreedEntity>
    ): MediatorResult {
        return try{
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.dbId / state.config.pageSize) + 1
                    }
                }
            }

            val cats = catApi.getCats(page = loadKey, limit = state.config.pageSize)

            catDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    catDb.dao.deleteAll()
                }
                val catBreedEntities = cats.map {it.toCatBreedEntity()}
                catDb.dao.upsertAll(catBreedEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = cats.isEmpty()
            )

        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}