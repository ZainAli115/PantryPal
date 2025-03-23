package com.example.pantrypal.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object RecipeSuggestion : Screen("suggestions")
    object RecipeDetail : Screen("details")

}