package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLyricsFontSizeUseCase @Inject constructor(
    private val repository: SongRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getLyricsFontSize()
    }
}