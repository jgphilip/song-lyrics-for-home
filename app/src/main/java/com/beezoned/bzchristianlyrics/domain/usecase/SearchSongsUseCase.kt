package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.model.Song
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchSongsUseCase @Inject constructor(
    private val repository: SongRepository
) {
    operator fun invoke(query: String): Flow<List<Song>> {
        return repository.getAllSongs().map { songs ->
            if (query.isBlank()) {
                songs
            } else {
                songs.filter { song ->
                    song.title.contains(query, ignoreCase = true) ||
                    song.lyrics.contains(query, ignoreCase = true) ||
                    song.author.contains(query, ignoreCase = true)
                }
            }
        }
    }
}