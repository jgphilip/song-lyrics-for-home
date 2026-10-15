package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LyricsFontSizeUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend fun setFontSize(size: Int) {
        songRepository.setLyricsFontSize(size)
    }

    fun getFontSize(): Flow<Int> {
        return songRepository.getLyricsFontSize()
    }
}