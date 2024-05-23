package com.luanafernandes.catapp.domain.model

data class CatBreed(
    val description: String,
    val id: String,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val referenceImageId: String?,
    val temperament: String
) {
}