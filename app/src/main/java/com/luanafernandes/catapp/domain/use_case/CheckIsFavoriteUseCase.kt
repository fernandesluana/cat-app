package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepository
import javax.inject.Inject

class CheckIsFavoriteUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke(catId: String): Boolean {
        return repository.isFavorite(catId)
    }
}