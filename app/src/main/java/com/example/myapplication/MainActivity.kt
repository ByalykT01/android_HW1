package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),
    LoginFragment.LoginEventHandler,
    RegisterFragment.RegisterEventHandler,
    RecipeAdapter.RecipeClickListener {

    val credentialsManager by lazy { CredentialsManager(this) }

    private val recipes = listOf(
        Recipe(1, "Spaghetti Carbonara", R.drawable.recipe1),
        Recipe(2, "Chicken Curry", R.drawable.recipe2),
        Recipe(3, "Caesar Salad", R.drawable.recipe3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, LoginFragment())
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recipesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecipeAdapter(recipes, this)
    }

    override fun onLoginSuccess() {
        findViewById<View>(R.id.fragment_container_view).visibility = View.GONE
        findViewById<RecyclerView>(R.id.recipesRecyclerView).visibility = View.VISIBLE
        setupRecyclerView()
    }

    override fun onRegisterClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, RegisterFragment())
            addToBackStack(null)
        }
    }

    override fun onRegisterSuccess() {
        supportFragmentManager.popBackStack()
    }

    // RecipeAdapter.RecipeClickListener implementations
    override fun onRecipeClick(recipe: Recipe) {
        Toast.makeText(this, "Clicked recipe: ${recipe.id}", Toast.LENGTH_SHORT).show()
        Log.d("Recipe", "Clicked recipe with id: ${recipe.id}")

        // Optional: Navigate to recipe details
        val intent = Intent(this, RecipeDetailActivity::class.java).apply {
            putExtra("RECIPE_ID", recipe.id)
        }
        startActivity(intent)
    }

    override fun onLikeClick(recipe: Recipe) {
        Toast.makeText(
            this,
            if (recipe.isLiked) "Liked recipe: ${recipe.id}"
            else "Unliked recipe: ${recipe.id}",
            Toast.LENGTH_SHORT
        ).show()
        Log.d("Recipe", "Toggled like for recipe with id: ${recipe.id}")
    }

    override fun onShareClick(recipe: Recipe) {
        Toast.makeText(this, "Sharing recipe: ${recipe.id}", Toast.LENGTH_SHORT).show()
        Log.d("Recipe", "Sharing recipe with id: ${recipe.id}")

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this recipe: ${recipe.title}")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share Recipe"))
    }
}