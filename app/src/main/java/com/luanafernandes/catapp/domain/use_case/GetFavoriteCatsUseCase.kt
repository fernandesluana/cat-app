package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepository
import com.luanafernandes.catapp.domain.model.CatBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCatsUseCase @Inject constructor(
    private val repository: CatRepository
) {
    operator fun invoke(): Flow<List<CatBreed>> {
        return repository.getFavoriteCats()
    }
}