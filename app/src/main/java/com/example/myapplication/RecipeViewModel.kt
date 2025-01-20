package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val allRecipes = listOf(
        Recipe(
            1,
            "Spaghetti Carbonara",
            R.drawable.recipe1,
            description = "Classic Italian pasta dish with eggs, cheese, pancetta, and pepper"
        ), Recipe(
            2,
            "Chicken Curry",
            R.drawable.recipe2,
            description = "Spicy Indian curry with tender chicken pieces"
        ), Recipe(
            3,
            "Caesar Salad",
            R.drawable.recipe3,
            description = "Fresh romaine lettuce with creamy dressing and croutons"
        )
    )

    private val _filteredRecipes = MutableStateFlow(allRecipes)
    val filteredRecipes = _filteredRecipes.asStateFlow()

    fun updateSearchQuery(query: String) {
        if (_searchQuery.value == query) return // Don't reload if query hasn't changed

        _searchQuery.value = query
        filterRecipes()
    }

    private fun filterRecipes() {
        val currentQuery = searchQuery.value
        _filteredRecipes.value = when {
            currentQuery.length < 3 -> allRecipes
            else -> allRecipes.filter { recipe ->
                recipe.title.contains(
                    currentQuery,
                    ignoreCase = true
                ) || recipe.description.contains(currentQuery, ignoreCase = true)
            }
        }
    }
}