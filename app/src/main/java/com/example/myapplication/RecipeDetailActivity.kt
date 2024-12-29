package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)

        val recipe = when (recipeId) {
            1 -> Recipe(1, "Spaghetti Carbonara", R.drawable.recipe1)
            2 -> Recipe(2, "Chicken Curry", R.drawable.recipe2)
            3 -> Recipe(3, "Caesar Salad", R.drawable.recipe3)
            else -> null
        }

        recipe?.let {
            findViewById<ImageView>(R.id.detailRecipeImage).setImageResource(it.imageResId)
            findViewById<TextView>(R.id.detailRecipeTitle).text = it.title
        }
    }
}