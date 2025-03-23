package com.example.pantrypal.network

data class RecipeResponse(
    val recipes: List<Recipe>
)

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val cookingTime: String,
    val difficulty: String,
    val calories: Int,
    val instructions: List<String>
)

// Add Chat Completion Model
data class ChatCompletionResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)