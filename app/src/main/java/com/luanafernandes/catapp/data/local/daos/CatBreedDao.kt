package com.luanafernandes.catapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luanafernandes.catapp.data.local.entities.CatBreedEntity

@Dao
interface CatBreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(catBreeds: List<CatBreedEntity>)

    @Query("Delete FROM cat_breed")
    suspend fun deleteAll()

    @Query("SELECT * FROM cat_breed")
    fun getAllCatBreeds(): PagingSource<Int, CatBreedEntity>

    @Query("SELECT * FROM cat_breed WHERE id = :id")
    suspend fun getCatBreedById(id: String): CatBreedEntity

    @Query("SELECT * FROM cat_breed WHERE name LIKE :query")
    fun getCatBreedsByName(query: String): PagingSource<Int, CatBreedEntity>


}