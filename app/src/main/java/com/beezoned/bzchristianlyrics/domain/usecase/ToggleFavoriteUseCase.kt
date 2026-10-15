package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(songId: String): Boolean {
        return repository.toggleFavorite(songId)
    }
}