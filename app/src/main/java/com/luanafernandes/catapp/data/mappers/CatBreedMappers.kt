package com.luanafernandes.catapp.data.mappers

import com.luanafernandes.catapp.data.local.CatBreedEntity
import com.luanafernandes.catapp.data.remote.CatBreedDto
import com.luanafernandes.catapp.domain.model.CatBreed

fun CatBreedDto.toCatBreedEntity(): CatBreedEntity{
    return CatBreedEntity(
        id = id,
        name = name,
        temperament = temperament,
        description = description,
        lifeSpan = life_span,
        referenceImageId = reference_image_id,
        origin = origin
    )
}

fun CatBreedEntity.toCatBreed(): CatBreed{
    return CatBreed(
        id = id,
        name = name,
        temperament = temperament,
        description = description,
        lifeSpan = lifeSpan,
        referenceImageId = referenceImageId,
        origin = origin
    )
}