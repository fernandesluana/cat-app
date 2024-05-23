package com.luanafernandes.catapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CatBreedEntity::class],
    version = 1
)
abstract class CatBreedDatabase: RoomDatabase() {
    abstract val dao :CatBreedDao
}