package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAverageLifespanOfFavoriteCatsUseCase @Inject constructor(
    private val repository: CatRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getAverageLifespanOfFavoriteBreeds()
    }
}