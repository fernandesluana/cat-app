package com.luanafernandes.catapp.domain.use_case

import com.luanafernandes.catapp.data.repository.CatRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAverageLifespanUseCase @Inject constructor(
    private val repository: CatRepositoryImpl
) {
    operator fun invoke(): Flow<Int> {
        return repository.getAverageLifespan()
    }
}