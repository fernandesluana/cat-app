package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.local.entities.FavoriteCatsEntity
import com.luanafernandes.catapp.data.repository.CatRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private val repository = mockk<CatRepositoryImpl>()
    private val toggleFavoriteUseCase = ToggleFavoriteUseCase(repository)

    @Test
    fun toggleFavorite_turnTrue_whenFavoriteIsFalse() = runBlocking {
        // GIVEN
        val newFavoriteCat = FavoriteCatsEntity(
            id = "AVR2",
            description = "intelligent",
            temperament = "active",
            name = "Avarice",
            imageUrl = "https://AVR2.com",
            origin = "United States",
            lifeSpan = "10 - 15",
        )
        val listOfFavoriteCats = mutableListOf<FavoriteCatsEntity>()

        // Mock repository to add the cat to the list
        coEvery { repository.toggleFavorite(newFavoriteCat.id, true) } answers {
            listOfFavoriteCats.add(newFavoriteCat)
        }

        // WHEN
        toggleFavoriteUseCase("AVR2", true)

        // THEN
        coVerify { repository.toggleFavorite("AVR2", true) }
    }

    @Test
    fun toggleFavorite_turnFalse_whenFavoriteIsTrue() = runBlocking {
        // GIVEN
        val newFavoriteCat = FavoriteCatsEntity(
            name = "Bombay",
            description = "Bombay Cat",
            id = "PRS1",
            lifeSpan = "13 - 15",
            origin = "Portugal",
            temperament = "affectionate, curious",
            imageUrl = "https://BMB1.com"
        )
        val listOfFavoriteCats = mutableListOf(newFavoriteCat)

        // Mock repository to remove the cat from the list
        coEvery { repository.toggleFavorite(newFavoriteCat.id, false) } answers {
            listOfFavoriteCats.remove(newFavoriteCat)
        }

        // WHEN
        toggleFavoriteUseCase("PRS1", false)

        // THEN
        coVerify { repository.toggleFavorite("PRS1", false) }
    }
}