package com.luanafernandes.catapp.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface CatBreedDao {

    @Upsert
    suspend fun upsertAll(catBreeds: List<CatBreedEntity>)
}