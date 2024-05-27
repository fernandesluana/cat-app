package com.luanafernandes.catapp.di

import android.content.Context
import androidx.room.Room
import com.luanafernandes.catapp.data.local.CatBreedDatabase
import com.luanafernandes.catapp.data.remote.CatApi
import com.luanafernandes.catapp.data.repository.CatRepository
import com.luanafernandes.catapp.domain.use_case.CheckIsFavoriteUseCase
import com.luanafernandes.catapp.domain.use_case.GetAllCatsUseCase
import com.luanafernandes.catapp.domain.use_case.GetAverageLifespanOfFavoriteCatsUseCase
import com.luanafernandes.catapp.domain.use_case.GetCatByIdUseCase
import com.luanafernandes.catapp.domain.use_case.GetCatByNameUseCase
import com.luanafernandes.catapp.domain.use_case.GetFavoriteCatsUseCase
import com.luanafernandes.catapp.domain.use_case.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCatApi(): CatApi {
        return Retrofit.Builder()
            .baseUrl(CatApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCatDatabase(@ApplicationContext context: Context): CatBreedDatabase {
        return Room.databaseBuilder(
            context,
            CatBreedDatabase::class.java,
            "cat_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCatRepository(
        catApi: CatApi,
        catDatabase: CatBreedDatabase
    ): CatRepository {
        return CatRepository(catApi, catDatabase)
    }

    @Provides
    @Singleton
    fun provideGetAllCatsUseCase(repository: CatRepository): GetAllCatsUseCase {
        return GetAllCatsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCatByIdUseCase(repository: CatRepository): GetCatByIdUseCase {
        return GetCatByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCatByNameUseCase(repository: CatRepository): GetCatByNameUseCase {
        return GetCatByNameUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(repository: CatRepository): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteCatsUseCase(repository: CatRepository): GetFavoriteCatsUseCase {
        return GetFavoriteCatsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsFavoriteUseCase(repository: CatRepository): CheckIsFavoriteUseCase {
        return CheckIsFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAverageLifespanOfFavoriteBreedsUseCase(repository: CatRepository): GetAverageLifespanOfFavoriteCatsUseCase {
        return GetAverageLifespanOfFavoriteCatsUseCase(repository)
    }

}









