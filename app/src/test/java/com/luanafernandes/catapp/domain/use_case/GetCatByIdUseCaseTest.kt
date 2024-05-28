package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepositoryImpl
import com.luanafernandes.catapp.domain.model.CatBreedFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class GetCatByIdUseCaseTest {

    private val repository = mockk<CatRepositoryImpl>()
    private lateinit var getCatByIdUseCase: GetCatByIdUseCase

    @Before
    fun setUp() {
        getCatByIdUseCase = GetCatByIdUseCase(repository)
    }

    @Test
    fun `test returns a existing cat by id`() = runBlocking{
        //GIVEN

        val listOfBreeds = CatBreedFactory.breeds
        val id = "SMS1"
        val expectedCat = listOfBreeds.find { it.id == id }!!

        coEvery { repository.getCatById(id) } returns expectedCat
        //WHEN
        val result = getCatByIdUseCase(id)
        // Then
        Assert.assertEquals(expectedCat, result)
    }

}