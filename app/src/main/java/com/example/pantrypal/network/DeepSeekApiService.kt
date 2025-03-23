package com.example.pantrypal.network

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DeepSeekApiService {
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    suspend fun getRecipes(
        @Body request: RecipeRequest
    ): ChatCompletionResponse

    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    suspend fun getTrendingRecipes(
        @Body request: TrendingRecipeRequest
    ): ChatCompletionResponse
}