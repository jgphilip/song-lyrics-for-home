package com.beezoned.bzchristianlyrics.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.beezoned.bzchristianlyrics.data.datasource.SongsDataSource
import com.beezoned.bzchristianlyrics.data.local.SongDao
import com.beezoned.bzchristianlyrics.data.local.SongDatabase
import com.beezoned.bzchristianlyrics.data.repository.SongRepositoryImpl
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import com.beezoned.bzchristianlyrics.domain.usecase.GetAllGenresUseCase
import com.beezoned.bzchristianlyrics.domain.usecase.GetAllSongsUseCase
import com.beezoned.bzchristianlyrics.domain.usecase.GetFavoriteSongsUseCase
import com.beezoned.bzchristianlyrics.domain.usecase.GetLyricsFontSizeUseCase
import com.beezoned.bzchristianlyrics.domain.usecase.GetSongByIdUseCase
import com.beezoned.bzchristianlyrics.domain.usecase.GetSongsByGenreUseCase
import com.beezoned.bzchristianlyrics.domain.usecase.SetLyricsFontSizeUseCase
import com.beezoned.bzchristianlyrics.domain.usecase.SongUseCases
import com.beezoned.bzchristianlyrics.domain.usecase.ToggleFavoriteUseCase
import com.beezoned.bzchristianlyrics.util.RecentlyViewedStore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSongDatabase(
        @ApplicationContext context: Context
    ): SongDatabase {
        return Room.databaseBuilder(
            context,
            SongDatabase::class.java,
            "songs_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSongDao(database: SongDatabase) = database.songDao()

    @Provides
    @Singleton
    fun provideSongsDataSource(
        @ApplicationContext context: Context,
        gson: Gson
    ): SongsDataSource {
        return SongsDataSource(context, gson)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSongRepository(
        songDao: SongDao,
        songsDataSource: SongsDataSource,
        dataStore: DataStore<Preferences>
    ): SongRepository {
        return SongRepositoryImpl(songDao, songsDataSource, dataStore)
    }

    @Provides
    @Singleton
    fun provideRecentlyViewedStore(dataStore: DataStore<Preferences>): RecentlyViewedStore {
        return RecentlyViewedStore(dataStore)
    }

    @Provides
    @Singleton
    fun provideSongUseCases(repository: SongRepository, recentlyViewedStore: RecentlyViewedStore): SongUseCases {
        return SongUseCases(
            getAllSongs = GetAllSongsUseCase(repository),
            getSongsByGenre = GetSongsByGenreUseCase(repository),
            getAllGenres = GetAllGenresUseCase(repository),
            getFavoriteSongs = GetFavoriteSongsUseCase(repository),
            toggleFavorite = ToggleFavoriteUseCase(repository),
            getSongById = GetSongByIdUseCase(repository),
            setLyricsFontSize = SetLyricsFontSizeUseCase(repository),
            getLyricsFontSize = GetLyricsFontSizeUseCase(repository)
        )
    }

//    @Provides
//    fun provideSearchSongsUseCase(repository: SongRepository): SearchSongsUseCase {
//        return SearchSongsUseCase(repository)
//    }

//    @Provides
//    fun provideGetRecentlyViewedSongsUseCase(
//        repository: SongRepository,
//        recentlyViewedStore: RecentlyViewedStore
//    ): GetRecentlyViewedSongsUseCase {
//        return GetRecentlyViewedSongsUseCase(repository, recentlyViewedStore)
//    }
//
//    @Provides
//    fun provideMarkSongAsViewedUseCase(recentlyViewedStore: RecentlyViewedStore): MarkSongAsViewedUseCase {
//        return MarkSongAsViewedUseCase(recentlyViewedStore)
//    }
//
//    @Provides
//    fun provideInitializeDatabaseUseCase(repository: SongRepository): InitializeDatabaseUseCase {
//        return InitializeDatabaseUseCase(repository)
//    }
//
//    @Provides
//    fun provideRefreshSongsUseCase(repository: SongRepository): RefreshSongsUseCase {
//        return RefreshSongsUseCase(repository)
//    }
}
