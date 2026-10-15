package com.beezoned.bzchristianlyrics.domain.usecase.advanced

import com.beezoned.bzchristianlyrics.domain.model.Song
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import com.beezoned.bzchristianlyrics.util.RecentlyViewedStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentlyViewedSongsUseCase @Inject constructor(
    private val repository: SongRepository,
    private val recentlyViewedStore: RecentlyViewedStore
) {
    operator fun invoke(limit: Int = 5): Flow<List<Song>> {
        return recentlyViewedStore.getRecentlyViewedIds().map { songIds ->
            songIds.mapNotNull { id ->
                repository.getSongById(id)
            }.take(limit)
        }
    }
}
