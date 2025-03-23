package com.example.pantrypal.viewmodel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pantrypal.network.Recipe
import com.example.pantrypal.network.RecipeRequest
import com.example.pantrypal.network.RecipeResponse
import com.example.pantrypal.network.RetrofitClient
import com.example.pantrypal.network.TrendingRecipeRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.StringReader

class RecipeViewModel : ViewModel() {
    var selectedRecipe: Recipe? = null
    var lastError: String? = null
    var trendingRecipes by mutableStateOf<List<Recipe>>(emptyList())
    var suggestedRecipes by mutableStateOf<List<Recipe>>(emptyList())
    var isLoading by mutableStateOf(false)

    suspend fun fetchTrendingRecipes() {
        if (trendingRecipes.isNotEmpty()) return  // Prevent re-fetching if data exists

        isLoading = true
        try {
            val response = RetrofitClient.api.getTrendingRecipes(TrendingRecipeRequest())
            val jsonResponse = response.choices.first().message.content

            // Clean the JSON response
            var cleanedJson = jsonResponse
                .replace("```json", "")   // Remove starting code fence if present
                .replace("```", "")        // Remove any trailing code fence
                .replace("\\boxed{", "")
                .replace("}}", "}")
                .trim()

            // Add root braces if missing
            if (!cleanedJson.startsWith("{") || !cleanedJson.endsWith("}")) {
                cleanedJson = "{$cleanedJson}"
            }

            Log.d("FINAL_JSON H", cleanedJson)

            // Parse with Gson
            val jsonReader = JsonReader(StringReader(cleanedJson)).apply {
                isLenient = true
            }
            val type = object : TypeToken<RecipeResponse>() {}.type
            trendingRecipes = Gson().fromJson<RecipeResponse>(jsonReader, type).recipes
        } catch (e: Exception) {
            lastError = "Error: ${e.message}"
            Log.e("API_ERROR", "Parsing failed", e)
        } finally {
            isLoading = false
        }
    }

    suspend fun fetchSuggestedRecipes(ingredients: String) {
        // Optionally, you might want to clear previous suggestions here:
        suggestedRecipes = emptyList()
        isLoading = true
        lastError = null // Reset error state at the start

        try {
            // Call the API with the provided ingredients
            val response = RetrofitClient.api.getRecipes(RecipeRequest(ingredients))
            val jsonResponse = response.choices.first().message.content

            // Clean the JSON response similar to fetchTrendingRecipes()
            var cleanedJson = jsonResponse
                .replace("```json", "")   // Remove starting code fence if present
                .replace("```", "")        // Remove any trailing code fence
                .replace("\\boxed{", "")
                .replace("}}", "}")
                .trim()

            // Add root braces if missing
            if (!cleanedJson.startsWith("{") || !cleanedJson.endsWith("}")) {
                cleanedJson = "{$cleanedJson}"
            }

            Log.d("FINAL_JSON S", cleanedJson)

            // Parse the JSON using Gson
            val jsonReader = JsonReader(StringReader(cleanedJson)).apply {
                isLenient = true
            }
            val type = object : TypeToken<RecipeResponse>() {}.type
            suggestedRecipes = Gson().fromJson<RecipeResponse>(jsonReader, type).recipes
            lastError = null
        } catch (e: Exception) {
            lastError = "Error: ${e.message}"
            Log.e("API_ERROR", "Parsing failed", e)
        } finally {
            isLoading = false
        }
    }
}
