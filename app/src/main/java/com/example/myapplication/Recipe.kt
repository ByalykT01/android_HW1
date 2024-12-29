package com.example.myapplication

data class Recipe(
    val id: Int,
    val title: String,
    val imageResId: Int,
    var isLiked: Boolean = false
)