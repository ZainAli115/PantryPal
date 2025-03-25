package com.example.pantrypal.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pantrypal.navigation.Screen
import com.example.pantrypal.network.Recipe
import com.example.pantrypal.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: RecipeViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.fetchTrendingRecipes()
        }
    }

    Scaffold(
        topBar = {
            UpdatedTopAppBar()

        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screen.RecipeSuggestion.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Create Recipe",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "New Recipe",
                    fontWeight = FontWeight.Medium
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                viewModel.isLoading -> LoadingIndicator()
                viewModel.lastError != null -> ErrorMessage(message = viewModel.lastError!!)
                else -> Column(modifier = Modifier.fillMaxSize()) {
                    HeaderSection()
                    RecipeGrid(
                        recipes = viewModel.trendingRecipes,
                        onRecipeClick = { recipe ->
                            viewModel.selectedRecipe = recipe
                            navController.navigate(Screen.RecipeDetail.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "What's Cooking?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Discover trending recipes from our community",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun RecipeCards(recipe: Recipe, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Modern placeholder with subtle pattern
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
                            )
                        )
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Restaurant,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.1f),
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                )
            }

            // Content overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Trending badge
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.LocalFireDepartment,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            "Trending",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                // Bottom content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    RecipeMetadataRow(recipe)
                }
            }
        }
    }
}

@Composable
private fun RecipeMetadataRow(recipe: Recipe) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MetadataItem(
            icon = Icons.Rounded.Timer,
            text = recipe.cookingTime,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(modifier = Modifier.width(12.dp))
        MetadataItem(
            icon = Icons.Rounded.Grade,
            text = recipe.difficulty,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(modifier = Modifier.width(12.dp))
        MetadataItem(
            icon = Icons.Rounded.LocalFireDepartment,
            text = "${recipe.calories} kcal",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}

@Composable
private fun MetadataItem(
    icon: ImageVector, 
    text: String, 
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RecipeGrid(recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 280.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipes) { recipe ->
            RecipeCards(
                recipe = recipe,
                onClick = { onRecipeClick(recipe) }
            )
        }
    }
}