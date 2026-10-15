package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.model.Genre
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGenresUseCase @Inject constructor(
    private val repository: SongRepository
) {
    operator fun invoke(): Flow<List<Genre>> {
        return repository.getAllGenres()
    }
}