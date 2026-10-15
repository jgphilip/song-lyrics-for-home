package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.model.Song
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import javax.inject.Inject

class GetSongByIdUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(songId: String): Song? {
        return repository.getSongById(songId)
    }
}