package com.example.pantrypal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantrypal.screens.SplashScreen
import com.example.pantrypal.navigation.Screen
import com.example.pantrypal.screens.HomeScreen
import com.example.pantrypal.screens.RecipeDetailScreen
import com.example.pantrypal.screens.RecipeSuggestionScreen
import com.example.pantrypal.ui.theme.PantryPalTheme
import com.example.pantrypal.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantryPalTheme {
                var showSplash by remember { mutableStateOf(true) }
                
                if (showSplash) {
                    SplashScreen(onSplashFinished = { showSplash = false })
                } else {
                    val navController = rememberNavController()
                    val recipeViewModel: RecipeViewModel = viewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController, recipeViewModel)
                        }
                        composable(Screen.RecipeSuggestion.route) {
                            RecipeSuggestionScreen(navController = navController, recipeViewModel)
                        }
                        composable(Screen.RecipeDetail.route) {
                            RecipeDetailScreen(recipeViewModel)
                        }
                    }
                }
            }
        }
    }
}