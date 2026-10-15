package com.beezoned.bzchristianlyrics.domain.repository

import com.beezoned.bzchristianlyrics.domain.model.Genre
import com.beezoned.bzchristianlyrics.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun getAllSongs(): Flow<List<Song>>
    fun getSongsByGenre(genreId: String): Flow<List<Song>>
    fun getAllGenres(): Flow<List<Genre>>
    fun getFavoriteSongs(): Flow<List<Song>>
    suspend fun toggleFavorite(songId: String) : Boolean
    suspend fun getSongById(songId: String): Song?
    suspend fun setLyricsFontSize(size: Int)
    fun getLyricsFontSize(): Flow<Int>
}