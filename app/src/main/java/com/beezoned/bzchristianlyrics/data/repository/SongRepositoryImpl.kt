package com.beezoned.bzchristianlyrics.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.beezoned.bzchristianlyrics.data.datasource.SongsDataSource
import com.beezoned.bzchristianlyrics.data.local.SongDao
import com.beezoned.bzchristianlyrics.data.local.entity.toSong
import com.beezoned.bzchristianlyrics.domain.model.Genre
import com.beezoned.bzchristianlyrics.domain.model.Song
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import com.beezoned.bzchristianlyrics.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songDao: SongDao,
    private val songsDataSource: SongsDataSource,
    private val dataStore: DataStore<Preferences>
) : SongRepository {

    companion object {
        private val LYRICS_FONT_SIZE_KEY = intPreferencesKey("lyrics_font_size")
        private const val DEFAULT_FONT_SIZE = 16
    }

    private val initializationMutex = Mutex()

    /**
     * Initialize the local database from the bundled catalog if it is empty.
     */
    private suspend fun initializeDatabase() = initializationMutex.withLock {
        if (songDao.getSongCount() == 0) {
            val songs = songsDataSource.fetchSongs()
            check(songs.isNotEmpty()) { "The bundled songs catalog is empty." }
            songDao.insertSongs(songs)
        }
    }

    /**
     * Refresh the local database with the latest songs from the remote source
     */
    suspend fun refreshSongs() {
        try {
            val remoteSongs = songsDataSource.fetchSongs()
            songDao.insertSongs(remoteSongs.map { it })
        } catch (e: Exception) {
            // Handle refresh failure
        }
    }

    override fun getAllSongs(): Flow<List<Song>> {
        return songDao.getAllSongs().onStart {
            initializeDatabase()
        }.map { entities ->
            entities.map { it.toSong() }
        }
    }

    override fun getSongsByGenre(genreId: String): Flow<List<Song>> {
        return songDao.getSongsByGenre(genreId).onStart {
            initializeDatabase()
        }.map { entities ->
            entities.map { it.toSong() }
        }
    }

    override fun getAllGenres(): Flow<List<Genre>> {
        return songDao.getUniqueGenres().onStart {
            initializeDatabase()
        }.map { genreIds ->
            genreIds.map { genreId ->
                Genre(
                    id = genreId,
                    name = Constants.GENRES.find { it.replace(" ", "").lowercase() == genreId } ?: genreId
                )
            }
        }
    }

    override fun getFavoriteSongs(): Flow<List<Song>> {
        return songDao.getFavoriteSongs().onStart {
            initializeDatabase()
        }.map { entities ->
            entities.map { it.toSong() }
        }
    }

    override suspend fun toggleFavorite(songId: String): Boolean {
        val song = songDao.getSongById(songId)
        if (song != null) {
            val updatedSong = song.copy(isFavorite = !song.isFavorite)
            songDao.updateSong(updatedSong)
            return updatedSong.isFavorite
        }
        return false
    }

    override suspend fun getSongById(songId: String): Song? {
        return songDao.getSongById(songId)?.toSong()
    }

    override suspend fun setLyricsFontSize(size: Int) {
        dataStore.edit { preferences ->
            preferences[LYRICS_FONT_SIZE_KEY] = size
        }
    }

    override fun getLyricsFontSize(): Flow<Int> {
        return flow {
            dataStore.data.collect { preferences ->
                emit(preferences[LYRICS_FONT_SIZE_KEY] ?: DEFAULT_FONT_SIZE)
            }
        }
    }
}