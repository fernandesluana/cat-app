package com.luanafernandes.catapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CatBreedDao {

    @Upsert
    suspend fun upsertAll(catBreeds: List<CatBreedEntity>)

    @Query("Delete FROM cat_breed")
    suspend fun deleteAll()

    @Query("SELECT * FROM cat_breed")
    fun pagingSource(): PagingSource<Int, CatBreedEntity>


}