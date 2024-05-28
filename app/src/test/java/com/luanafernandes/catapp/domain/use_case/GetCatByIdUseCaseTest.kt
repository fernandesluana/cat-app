package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepositoryImpl
import com.luanafernandes.catapp.domain.model.CatBreedFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetCatByIdUseCaseTest {

    private val repository = mockk<CatRepositoryImpl>()
    private val getCatByIdUseCase = GetCatByIdUseCase(repository)

    @Test
    fun getCatById_success() = runBlocking {
        //GIVEN
        val id = "SMS1"
        val listOfBreeds = CatBreedFactory.breeds
        coEvery { repository.getCatById(id) } returns listOfBreeds.find { it.id == id }!!
        //WHEN
        val result = getCatByIdUseCase(id)
        //THEN
        Assert.assertEquals(id, result.id)
    }

}