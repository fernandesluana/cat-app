package com.luanafernandes.catapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.luanafernandes.catapp.data.mappers.toCatBreed
import com.luanafernandes.catapp.data.repository.CatRepository
import com.luanafernandes.catapp.domain.model.CatBreed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val _catBreed = MutableLiveData<CatBreed>()
    val catBreed: LiveData<CatBreed> get() = _catBreed

    val favoriteCats: LiveData<List<CatBreed>> =
    repository.getFavoriteCats().asLiveData()

    val getAllCats: Flow<PagingData<CatBreed>> = repository.getAllCats()
        .map { pagingData ->
            pagingData.map { catBreedEntity ->
                catBreedEntity.toCatBreed()
            }
        }
        .cachedIn(viewModelScope)

    fun toggleFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(catId, isFavorite)
        }
    }

    suspend fun isFavorite(catId: String): Boolean {
        return repository.isFavorite(catId)
    }

    fun fetchCatBreedById(catId: String) {
        viewModelScope.launch {
            val cat = repository.getCatBreed(catId)
            _catBreed.value = cat
        }
    }

    fun getCatBreeds(query: String): Flow<PagingData<CatBreed>> {
        return repository.searchCatBreeds(query)
            .map { pagingData ->
                pagingData.map { catBreedEntity ->
                    catBreedEntity.toCatBreed()
                }
            }
            .cachedIn(viewModelScope)
    }

    val averageLifespan: Flow<Int> = repository.getAverageLifespanOfFavoriteBreeds()

}