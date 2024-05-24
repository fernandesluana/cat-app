package com.luanafernandes.catapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CatBreedEntity::class, CatBreedsRemoteKeysEntity::class],
    version = 1
)
abstract class CatBreedDatabase: RoomDatabase() {
    abstract fun catBreedsDao(): CatBreedDao
    abstract fun catBreedsRemoteKeysDao(): CatBreedsRemoteKeysDao

}