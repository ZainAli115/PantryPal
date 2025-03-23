package com.example.pantrypal.network

data class RecipeRequest(
    val ingredients: String,
    val model: String = "deepseek/deepseek-r1-zero:free",
    val messages: List<Message> = listOf(
        Message(
            role = "user",
            content = """
                Generate 5 recipes using only $ingredients. Respond STRICTLY with valid JSON:
                - Use double quotes for keys and string values
                - Escape internal double quotes with backslash
                - No trailing commas
                - No markdown/Latex formatting
                Example format:
                {
                    "recipes": [{
                        "id": 1,
                        "title": "Sample Dish",
                        "description": "A sample description",
                        "cookingTime": "30 mins",
                        "difficulty": "Easy",
                        "calories": 500,
                        "instructions": ["Step 1", "Step 2"]
                    }]
                }
                """
        )
    )
)

data class TrendingRecipeRequest(
    val model: String = "deepseek/deepseek-r1-zero:free",
    val messages: List<Message> = listOf(
        Message(
            role = "user",
            content = """
                Suggest 5 trending recipes. Respond STRICTLY with valid JSON:
                - Use double quotes for keys and string values
                - Escape internal quotes with backslash
                - No markdown/Latex
                - Properly formatted arrays
                Example format:
                {
                    "recipes": [{
                        "id": 1,
                        "title": "Trending Dish",
                        "description": "Popular recipe",
                        "cookingTime": "45 mins",
                        "difficulty": "Medium",
                        "calories": 650,
                        "instructions": ["Step 1", "Step 2"]
                    }]
                }
                """
        )
    )
)