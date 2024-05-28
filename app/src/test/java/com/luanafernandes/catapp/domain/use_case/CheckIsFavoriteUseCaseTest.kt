package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepositoryImpl
import com.luanafernandes.catapp.domain.model.FavoriteCatsFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CheckIsFavoriteUseCaseTest {

    private val repository = mockk<CatRepositoryImpl>()
    private lateinit var checkIsFavorite: CheckIsFavoriteUseCase

    @Before
    fun setup() {
        checkIsFavorite = CheckIsFavoriteUseCase(repository)
    }

    @Test
    fun `checkIsFavorite with existing favorite ID returns true`() = runBlocking {
        // GIVEN
        val id = "CHS1"
        val listOfFavorites = FavoriteCatsFactory.favoriteCats
        coEvery { repository.isFavorite(id) } returns listOfFavorites.any { it.id == id }

        // WHEN
        val result = checkIsFavorite(id)

        // THEN
        Assert.assertTrue(result)
    }

    @Test
    fun `checkIsFavorite with non-existing favorite ID returns false`() = runBlocking {
        // GIVEN
        val id = "ABC1"
        val listOfFavorites = FavoriteCatsFactory.favoriteCats
        coEvery { repository.isFavorite(id) } returns listOfFavorites.any { it.id == id }

        // WHEN
        val result = checkIsFavorite(id)

        // THEN
        Assert.assertFalse(result)
    }
}