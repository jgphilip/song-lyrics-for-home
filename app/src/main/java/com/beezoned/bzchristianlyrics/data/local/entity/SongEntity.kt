package com.beezoned.bzchristianlyrics.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beezoned.bzchristianlyrics.domain.model.Song

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey val id: String,
    val title: String,
    val artist: String,
    val author: String,
    val language: String,
    val genreId: String,
    val lyrics: String,
    val isFavorite: Boolean = false
)

fun SongEntity.toSong(): Song {
    return Song(
        id = id,
        title = title,
        artist = artist,
        author = author,
        language = language,
        genreId = genreId,
        lyrics = lyrics,
        isFavorite = isFavorite
    )
}

fun Song.toSongEntity(): SongEntity {
    return SongEntity(
        id = id,
        title = title,
        artist = artist,
        author = author,
        language = language,
        genreId = genreId,
        lyrics = lyrics,
        isFavorite = isFavorite
    )
}