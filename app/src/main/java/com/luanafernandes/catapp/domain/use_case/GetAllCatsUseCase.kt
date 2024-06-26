package com.luanafernandes.catapp.domain.use_case

import androidx.paging.PagingData
import com.luanafernandes.catapp.data.local.entities.CatBreedEntity
import com.luanafernandes.catapp.data.repository.CatRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCatsUseCase @Inject constructor(
    private val repository: CatRepositoryImpl
){
    operator fun invoke(): Flow<PagingData<CatBreedEntity>> {
        return repository.getAllCats()
    }
}