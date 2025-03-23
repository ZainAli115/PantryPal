package com.example.pantrypal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                val navController = rememberNavController()
                val recipeViewModel: RecipeViewModel = viewModel() // Create ViewModel once

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController, recipeViewModel) // Pass shared ViewModel
                    }
                    composable(Screen.RecipeSuggestion.route) {
                        RecipeSuggestionScreen(navController = navController,recipeViewModel) // Pass shared ViewModel
                    }
                    composable(Screen.RecipeDetail.route) {
                        RecipeDetailScreen(recipeViewModel) // Use the same instance
                    }
                }

            }
        }
    }
}