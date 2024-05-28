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
import com.luanafernandes.catapp.domain.model.CatBreed
import com.luanafernandes.catapp.domain.use_case.CheckIsFavoriteUseCase
import com.luanafernandes.catapp.domain.use_case.GetAllCatsUseCase
import com.luanafernandes.catapp.domain.use_case.GetAverageLifespanUseCase
import com.luanafernandes.catapp.domain.use_case.GetCatByIdUseCase
import com.luanafernandes.catapp.domain.use_case.GetCatByNameUseCase
import com.luanafernandes.catapp.domain.use_case.GetFavoriteCatsUseCase
import com.luanafernandes.catapp.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getAllCatsUseCase: GetAllCatsUseCase,
    private val getCatByIdUseCase: GetCatByIdUseCase,
    private val getCatByNameUseCase: GetCatByNameUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
    private val getAverageLifespanUseCase: GetAverageLifespanUseCase
) : ViewModel() {

    private val _catBreed = MutableLiveData<CatBreed>()
    val catBreed: LiveData<CatBreed> get() = _catBreed

    val favoriteCats: LiveData<List<CatBreed>> =
    getFavoriteCatsUseCase().asLiveData()

    val getAllCats: Flow<PagingData<CatBreed>> = getAllCatsUseCase()
        .map { pagingData ->
            pagingData.map { catBreedEntity ->
                catBreedEntity.toCatBreed()
            }
        }
        .cachedIn(viewModelScope)

    fun toggleFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(catId, isFavorite)
        }
    }

    suspend fun isFavorite(catId: String): Boolean {
        return checkIsFavoriteUseCase(catId)
    }

    fun fetchCatBreedById(catId: String) {
        viewModelScope.launch {
            val cat = getCatByIdUseCase(catId)
            _catBreed.value = cat
        }
    }

    fun getCatBreeds(query: String): Flow<PagingData<CatBreed>> {
        return getCatByNameUseCase(query)
            .map { pagingData ->
                pagingData.map { catBreedEntity ->
                    catBreedEntity.toCatBreed()
                }
            }
            .cachedIn(viewModelScope)
    }

    val averageLifespan: Flow<Int> = getAverageLifespanUseCase()

}