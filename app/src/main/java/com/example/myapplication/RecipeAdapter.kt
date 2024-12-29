package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(private val recipes: List<Recipe>, private val listener: RecipeClickListener) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    interface RecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
        fun onLikeClick(recipe: Recipe)
        fun onShareClick(recipe: Recipe)
    }

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.recipeImage)
        val titleText: TextView = view.findViewById(R.id.recipeTitle)
        val likeButton: ImageButton = view.findViewById(R.id.likeButton)
        val shareButton: ImageButton = view.findViewById(R.id.shareButton)
        val cardView: CardView = view.findViewById(R.id.recipeCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.imageView.setImageResource(recipe.imageResId)
        holder.titleText.text = recipe.title
        holder.likeButton.setImageResource(
            if (recipe.isLiked) R.drawable.ic_heart_filled
            else R.drawable.ic_heart
        )

        holder.cardView.setOnClickListener {
            listener.onRecipeClick(recipe)
        }

        holder.likeButton.setOnClickListener {
            recipe.isLiked = !recipe.isLiked
            listener.onLikeClick(recipe)
            notifyItemChanged(position)
        }

        holder.shareButton.setOnClickListener {
            listener.onShareClick(recipe)
        }
    }

    override fun getItemCount() = recipes.size
}