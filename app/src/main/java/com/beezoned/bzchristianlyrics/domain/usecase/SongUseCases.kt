package com.beezoned.bzchristianlyrics.domain.usecase

import javax.inject.Inject

// A convenience class that bundles all song-related use cases together
class SongUseCases @Inject constructor(
    val getAllSongs: GetAllSongsUseCase,
    val getSongsByGenre: GetSongsByGenreUseCase,
    val getAllGenres: GetAllGenresUseCase,
    val getFavoriteSongs: GetFavoriteSongsUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val getSongById: GetSongByIdUseCase,
    val setLyricsFontSize: SetLyricsFontSizeUseCase,
    val getLyricsFontSize: GetLyricsFontSizeUseCase
)