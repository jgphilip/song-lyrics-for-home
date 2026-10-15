package com.beezoned.bzchristianlyrics.presentation.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beezoned.bzchristianlyrics.domain.model.Song
import com.beezoned.bzchristianlyrics.domain.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val songRepository: SongRepository
) : ViewModel() {

    private val _genres = MutableStateFlow<List<String>>(emptyList())
    val genres: StateFlow<List<String>> = _genres.asStateFlow()

    private val _songsByGenre = MutableStateFlow<List<Song>>(emptyList())
    val songsByGenre: StateFlow<List<Song>> = _songsByGenre.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _isLoading.value = true
            songRepository.getAllSongs().collect { songs ->
                _genres.value = songs.map { it.genreId }.distinct().sorted()
                _isLoading.value = false
            }
        }
    }

    fun loadSongsByGenre(genre: String) {
        viewModelScope.launch {
            _isLoading.value = true
            songRepository.getSongsByGenre(genre).collect { songs ->
                _songsByGenre.value = songs
                _isLoading.value = false
            }
        }
    }
}
