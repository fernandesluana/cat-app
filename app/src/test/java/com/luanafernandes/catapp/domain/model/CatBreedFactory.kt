package com.luanafernandes.catapp.domain.model

object CatBreedFactory {

    val breeds = listOf(
        CatBreed(
            name = "Persian",
            description = "Persian Cat",
            id = "PRS1",
            lifeSpan = "13 - 15",
            origin = "United States",
            temperament = "calm, friendly, outgoing",
            isFavorite = false,
            imageUrl = "https://PRS1.com"
        ),
        CatBreed(
            name = "Siamese",
            description = "Siamese Cat",
            id = "SMS1",
            lifeSpan = "12 - 17",
            origin = "Canada",
            temperament = "calm",
            isFavorite = false,
            imageUrl = "https://SMS1.com"
        ),
        CatBreed(
            name = "Bombay",
            description = "Bombay Cat",
            id = "PRS1",
            lifeSpan = "13 - 15",
            origin = "Portugal",
            temperament = "affectionate, curious",
            isFavorite = false,
            imageUrl = "https://BMB1.com"
        ),
        CatBreed(
            name = "Chausie",
            description = "Chausie Cat",
            id = "CHS1",
            lifeSpan = "11 - 11",
            origin = "United Kingdom",
            temperament = "outgoing, active",
            isFavorite = false,
            imageUrl = "https://Chs1.com"
        ),
        CatBreed(
            name = "Burmese",
            description = "Egypt",
            id = "BRS1",
            lifeSpan = "13 - 15",
            origin = "United States",
            temperament = "intelligent",
            isFavorite = false,
            imageUrl = "https://BRMS.com"
        )
    )
}