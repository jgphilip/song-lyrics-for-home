package com.beezoned.bzchristianlyrics.util

import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// A helper class to manage recently viewed songs
class RecentlyViewedStore @Inject constructor(
    private val dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>
) {
    private val RECENTLY_VIEWED_KEY = androidx.datastore.preferences.core.stringPreferencesKey("recently_viewed_songs")
    private val MAX_RECENT_SONGS = 10

    suspend fun addToRecentlyViewed(songId: String) {
        dataStore.edit { preferences ->
            val current = preferences[RECENTLY_VIEWED_KEY]?.split(",") ?: emptyList()
            val updated = (listOf(songId) + current.filter { it != songId }).take(MAX_RECENT_SONGS)
            preferences[RECENTLY_VIEWED_KEY] = updated.joinToString(",")
        }
    }

    fun getRecentlyViewedIds(): Flow<List<String>> {
        return dataStore.data.map { preferences ->
            preferences[RECENTLY_VIEWED_KEY]?.split(",") ?: emptyList()
        }
    }
}