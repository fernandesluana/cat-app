package com.luanafernandes.catapp.data.local.entities

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
    val temperament: String,
    val imageUrl: String
){

}