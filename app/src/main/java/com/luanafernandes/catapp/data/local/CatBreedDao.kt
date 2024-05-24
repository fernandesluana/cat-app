package com.luanafernandes.catapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatBreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(catBreeds: List<CatBreedEntity>)

    @Query("Delete FROM cat_breed")
    suspend fun deleteAll()

    @Query("SELECT * FROM cat_breed")
    fun getAllCatBreeds(): PagingSource<Int, CatBreedEntity>


}