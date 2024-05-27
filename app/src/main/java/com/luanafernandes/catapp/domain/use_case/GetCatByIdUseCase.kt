package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepository
import com.luanafernandes.catapp.domain.model.CatBreed
import javax.inject.Inject

class GetCatByIdUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke(id: String): CatBreed {
        return repository.getCatBreed(id)
    }
}