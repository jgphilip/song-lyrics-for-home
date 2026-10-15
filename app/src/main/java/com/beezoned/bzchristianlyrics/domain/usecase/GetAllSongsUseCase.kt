package com.beezoned.bzchristianlyrics.domain.usecase

import com.beezoned.bzchristianlyrics.domain.model.Song
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(
    private val repository: SongRepository
) {
    operator fun invoke(): Flow<List<Song>> {
        return repository.getAllSongs()
    }
}