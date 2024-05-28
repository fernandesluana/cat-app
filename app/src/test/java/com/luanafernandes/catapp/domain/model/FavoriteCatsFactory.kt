package com.luanafernandes.catapp.domain.model

import com.luanafernandes.catapp.data.local.entities.FavoriteCatsEntity

object FavoriteCatsFactory {

    val favoriteCats = listOf(
        FavoriteCatsEntity(
            name = "Persian",
            description = "Persian Cat",
            id = "PRS1",
            lifeSpan = "13 - 15",
            origin = "United States",
            temperament = "calm, friendly, outgoing",
            imageUrl = "https://PRS1.com"
        ),
        FavoriteCatsEntity(
            name = "Siamese",
            description = "Siamese Cat",
            id = "SMS1",
            lifeSpan = "12 - 17",
            origin = "Canada",
            temperament = "calm",
            imageUrl = "https://SMS1.com"
        ),
        FavoriteCatsEntity(
            name = "Bombay",
            description = "Bombay Cat",
            id = "PRS1",
            lifeSpan = "13 - 15",
            origin = "Portugal",
            temperament = "affectionate, curious",
            imageUrl = "https://BMB1.com"
        ),
        FavoriteCatsEntity(
            name = "Chausie",
            description = "Chausie Cat",
            id = "CHS1",
            lifeSpan = "11 - 11",
            origin = "United Kingdom",
            temperament = "outgoing, active",
            imageUrl = "https://Chs1.com"
        ),
        FavoriteCatsEntity(
            name = "Burmese",
            description = "Egypt",
            id = "BRS1",
            lifeSpan = "13 - 15",
            origin = "United States",
            temperament = "intelligent",
            imageUrl = "https://BRMS.com"
        )

    )
}