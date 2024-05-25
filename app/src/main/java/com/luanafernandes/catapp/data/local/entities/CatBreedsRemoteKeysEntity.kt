package com.luanafernandes.catapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_breeds_remote_keys")
data class CatBreedsRemoteKeysEntity(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?,

) {
}