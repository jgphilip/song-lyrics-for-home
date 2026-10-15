package com.beezoned.bzchristianlyrics.domain.usecase.advanced

import com.beezoned.bzchristianlyrics.util.RecentlyViewedStore
import javax.inject.Inject

class MarkSongAsViewedUseCase @Inject constructor(
    private val recentlyViewedStore: RecentlyViewedStore
) {
    suspend operator fun invoke(songId: String) {
        recentlyViewedStore.addToRecentlyViewed(songId)
    }
}