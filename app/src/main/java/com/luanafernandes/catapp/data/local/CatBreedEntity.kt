package com.luanafernandes.catapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_breed")
data class CatBreedEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val description: String,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val referenceImageId: String?,
    val temperament: String
){

}