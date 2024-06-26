package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepositoryImpl
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CatRepositoryImpl
) {
    suspend operator fun invoke(catId: String, isFavorite: Boolean) {
        repository.toggleFavorite(catId, isFavorite)
    }
}