package com.luanafernandes.catapp.domain.use_case

import androidx.paging.PagingData
import com.luanafernandes.catapp.data.local.entities.CatBreedEntity
import com.luanafernandes.catapp.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatByNameUseCase @Inject constructor(
    private val repository: CatRepository
) {
    operator fun invoke(query: String): Flow<PagingData<CatBreedEntity>> {
        return repository.searchCatBreeds(query)
    }
}