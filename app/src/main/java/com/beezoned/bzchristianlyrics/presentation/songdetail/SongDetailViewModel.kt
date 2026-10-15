package com.beezoned.bzchristianlyrics.presentation.songdetail

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
class SongDetailViewModel @Inject constructor(
    private val songRepository: SongRepository
) : ViewModel() {

    private val _song: MutableStateFlow<Song?> = MutableStateFlow<Song?>(null)
    val song: StateFlow<Song?> = _song.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadSong(songId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _song.value = songRepository.getSongById(songId)
            _isLoading.value = false
        }
    }
}