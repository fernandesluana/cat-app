package com.luanafernandes.catapp.data.mappers

import com.luanafernandes.catapp.data.local.entities.CatBreedEntity
import com.luanafernandes.catapp.data.local.entities.FavoriteCatsEntity
import com.luanafernandes.catapp.data.remote.CatBreedDto
import com.luanafernandes.catapp.domain.model.CatBreed

fun CatBreedDto.toCatBreedEntity(imageUrl: String): CatBreedEntity {
    return CatBreedEntity(
        id = id,
        name = name,
        temperament = temperament,
        description = description,
        lifeSpan = life_span,
        origin = origin,
        imageUrl = imageUrl
    )
}

fun CatBreedEntity.toCatBreed(): CatBreed {
    return CatBreed(
        id = id,
        name = name,
        temperament = temperament,
        description = description,
        lifeSpan = lifeSpan,
        origin = origin,
        imageUrl = imageUrl
    )
}

fun FavoriteCatsEntity.toCatBreed(): CatBreed {
    return CatBreed(
        id = id,
        name = name,
        temperament = temperament,
        description = description,
        lifeSpan = lifeSpan,
        origin = origin,
        imageUrl = imageUrl
    )
}

fun CatBreedEntity.toFavoriteCatsEntity(): FavoriteCatsEntity {
    return FavoriteCatsEntity(
        id = id,
        name = name,
        temperament = temperament,
        description = description,
        lifeSpan = lifeSpan,
        origin = origin,
        imageUrl = imageUrl
    )

}