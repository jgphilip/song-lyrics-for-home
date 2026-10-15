package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import javax.inject.Inject

class SetLyricsFontSizeUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(size: Int) {
        // Validate font size before saving
        val validSize = when {
            size < 8 -> 8  // Minimum readable size
            size > 36 -> 36  // Maximum reasonable size
            else -> size
        }
        repository.setLyricsFontSize(validSize)
    }
}