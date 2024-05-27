package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke(catId: String, isFavorite: Boolean) {
        repository.toggleFavorite(catId, isFavorite)
    }
}