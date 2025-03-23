package com.example.pantrypal.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pantrypal.network.Recipe
import com.example.pantrypal.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(recipeViewModel: RecipeViewModel = viewModel()) {
    val recipe by remember { mutableStateOf(recipeViewModel.selectedRecipe) }

    Scaffold(
        topBar = { UpdatedTopAppBar() },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (recipe == null) {
                Text("Select a recipe to view details", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    item {
                        // Header with icon and title
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            // Icon in a decorative container
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape,
                                modifier = Modifier.size(80.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SetMeal,
                                    contentDescription = "Recipe illustration",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(16.dp))
                            }

                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = recipe!!.title,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    item { Spacer(Modifier.height(8.dp)) }

                    item {
                        // Metadata in a card
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.fillMaxWidth(),
                            shadowElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RecipeMetadataItem(
                                    icon = Icons.Default.Schedule,
                                    label = "Time",
                                    value = recipe!!.cookingTime
                                )
                                Divider(
                                    modifier = Modifier
                                        .height(24.dp)
                                        .width(1.dp)
                                        .padding(horizontal = 8.dp),
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                                RecipeMetadataItem(
                                    icon = Icons.Default.Whatshot,
                                    label = "Calories",
                                    value = "${recipe!!.calories} kcal"
                                )
                                Divider(
                                    modifier = Modifier
                                        .height(24.dp)
                                        .width(1.dp)
                                        .padding(horizontal = 8.dp),
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                                RecipeMetadataItem(
                                    icon = Icons.Default.Leaderboard,
                                    label = "Difficulty",
                                    value = recipe!!.difficulty
                                )
                            }
                        }
                    }

                    item { Spacer(Modifier.height(24.dp)) }

                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Instructions",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            InstructionsList(instructions = recipe!!.instructions)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeMetadataItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun InstructionsList(instructions: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        instructions.forEachIndexed { index, instruction ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.size(24.dp)
                ) {
                    Text(
                        text = "${index + 1}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Text(
                    text = instruction,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
            if (index < instructions.lastIndex) {
                Divider(
                    modifier = Modifier.padding(start = 40.dp, top = 8.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                )
            }
        }
    }
}



@Composable
private fun RecipeMetadata(cookingTime: String, difficulty: String, calories: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconLabel(
            icon = Icons.Default.Schedule,
            text = "$cookingTime mins",
            color = MaterialTheme.colorScheme.primary,
            iconSize = 24.dp
        )
        IconLabel(
            icon = Icons.Default.LocalFireDepartment,
            text = "$calories kcal",
            color = MaterialTheme.colorScheme.secondary,
            iconSize = 24.dp
        )
        IconLabel(
            icon = Icons.Default.Star,
            text = difficulty,
            color = MaterialTheme.colorScheme.tertiary,
            iconSize = 24.dp
        )
    }
}

@Composable
private fun IconLabel(icon: ImageVector, text: String, color: Color, iconSize: Dp) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(iconSize)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun IngredientsGrid(ingredients: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ingredients) { ingredient ->
            ChipItem(
                text = ingredient,
                leadingIcon = Icons.Default.LunchDining
            )
        }
    }
}

@Composable
private fun ChipItem(text: String, leadingIcon: ImageVector) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
private fun InstructionStep(stepNumber: Int, instruction: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(28.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "$stepNumber",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = instruction,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
