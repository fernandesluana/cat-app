package com.luanafernandes.catapp.data.remote

data class CatBreedDto(
    val description: String,
    val id: String,
    val life_span: String,
    val name: String,
    val origin: String,
    val reference_image_id: String?,
    val temperament: String
)