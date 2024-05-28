package com.luanafernandes.catapp.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luanafernandes.catapp.data.local.entities.FavoriteCatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatBreedsFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCat(cat: FavoriteCatsEntity)

    @Delete
    suspend fun deleteFavoriteCat(cat: FavoriteCatsEntity)

    @Query("SELECT * FROM favorite_cats")
    fun getFavoriteCats(): Flow<List<FavoriteCatsEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cats WHERE id = :id)")
    suspend fun containsId(id: String): Boolean

    @Query("SELECT AVG(CAST(SUBSTR(lifespan, 1, 2) AS INTEGER)) FROM favorite_cats")
    fun getAverageLifespan(): Flow<Int>
}