package com.luanafernandes.catapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luanafernandes.catapp.data.local.entities.CatBreedsRemoteKeysEntity

@Dao
interface CatBreedsRemoteKeysDao {

    @Query("SELECT * FROM cat_breeds_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: String): CatBreedsRemoteKeysEntity

    @Query("DELETE FROM cat_breeds_remote_keys")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CatBreedsRemoteKeysEntity>)


}