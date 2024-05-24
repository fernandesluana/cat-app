package com.luanafernandes.catapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/v1/breeds")
    suspend fun getCats(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<CatBreedDto>

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/"
        const val API_KEY = ""
    }
}