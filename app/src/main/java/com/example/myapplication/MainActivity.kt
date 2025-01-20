package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), LoginFragment.LoginEventHandler,
    RegisterFragment.RegisterEventHandler, RecipeAdapter.RecipeClickListener {

    val credentialsManager by lazy { CredentialsManager(this) }
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, LoginFragment())
            }
        }

        setupSearchView()
        setupRecipeListObserver()
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.updateSearchQuery(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.updateSearchQuery(it) }
                return true
            }
        })
    }

    private fun setupRecipeListObserver() {
        lifecycleScope.launch {
            viewModel.filteredRecipes.collectLatest { recipes ->
                val recyclerView = findViewById<RecyclerView>(R.id.recipesRecyclerView)
                recyclerView.adapter = RecipeAdapter(recipes, this@MainActivity)
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recipesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            viewModel.filteredRecipes.collectLatest { recipes ->
                recyclerView.adapter = RecipeAdapter(recipes, this@MainActivity)
            }
        }
    }

    override fun onLoginSuccess() {
        findViewById<View>(R.id.fragment_container_view).visibility = View.GONE
        findViewById<View>(R.id.content_container).visibility = View.VISIBLE
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

    override fun onRecipeClick(recipe: Recipe) {
        val intent = Intent(this, RecipeDetailActivity::class.java).apply {
            putExtra("RECIPE_ID", recipe.id)
        }
        startActivity(intent)
    }

    override fun onLikeClick(recipe: Recipe) {
        recipe.isLiked = !recipe.isLiked
        Toast.makeText(
            this, if (recipe.isLiked) "Liked recipe: ${recipe.title}"
            else "Unliked recipe: ${recipe.title}", Toast.LENGTH_SHORT
        ).show()
    }

    override fun onShareClick(recipe: Recipe) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "Check out this recipe: ${recipe.title}\n${recipe.description}"
            )
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_recipe)))
    }
}