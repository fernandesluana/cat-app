package com.luanafernandes.catapp.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luanafernandes.catapp.data.local.CatBreedDatabase
import com.luanafernandes.catapp.data.local.CatBreedEntity
import com.luanafernandes.catapp.data.paging.CatRemoteMediator
import com.luanafernandes.catapp.data.remote.CatApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CatRepository @Inject constructor(
    private val catApi: CatApi,
    private val catDatabase: CatBreedDatabase
) {

    fun getAllCats(): Flow<PagingData<CatBreedEntity>> {
        val pagingSourceFactory = { catDatabase.catBreedsDao().getAllCatBreeds() }
        return Pager(
            config = PagingConfig(pageSize = 15),
            remoteMediator = CatRemoteMediator(
                catApi = catApi,
                catDatabase = catDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    


}