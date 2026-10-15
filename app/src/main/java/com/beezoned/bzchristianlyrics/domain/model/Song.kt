package com.beezoned.bzchristianlyrics.domain.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val author: String,
    val language: String,
    val genreId: String,
    val lyrics: String,
    val isFavorite: Boolean = false
)