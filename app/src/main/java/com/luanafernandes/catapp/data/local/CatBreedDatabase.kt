package com.luanafernandes.catapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luanafernandes.catapp.data.local.daos.CatBreedDao
import com.luanafernandes.catapp.data.local.daos.CatBreedsFavoriteDao
import com.luanafernandes.catapp.data.local.daos.CatBreedsRemoteKeysDao
import com.luanafernandes.catapp.data.local.entities.CatBreedEntity
import com.luanafernandes.catapp.data.local.entities.CatBreedsRemoteKeysEntity
import com.luanafernandes.catapp.data.local.entities.FavoriteCatsEntity

@Database(
    entities = [CatBreedEntity::class, CatBreedsRemoteKeysEntity::class, FavoriteCatsEntity::class],
    version = 1
)
abstract class CatBreedDatabase: RoomDatabase() {
    abstract fun catBreedsDao(): CatBreedDao
    abstract fun catBreedsRemoteKeysDao(): CatBreedsRemoteKeysDao
    abstract fun favoriteCatsDao(): CatBreedsFavoriteDao

}