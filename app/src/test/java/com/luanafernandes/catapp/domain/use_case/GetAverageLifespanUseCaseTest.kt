package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.domain.model.CatBreedFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetAverageLifespanUseCaseTest{

    @Test
    fun `getAverageLifespan returns correct average lifespan`() = runBlocking {

        //GIVEN
        val catsList = CatBreedFactory.breeds
        val expectedAverageLifespan = 15

        val result: Int? = null

        // WHEN
        GetAverageLifespanUseCase(catsList)

        //THEN
        Assert.assertEquals(result, 15)
    }
}