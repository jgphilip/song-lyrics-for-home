package com.beezoned.bzchristianlyrics.data.datasource

import android.content.Context
import com.beezoned.bzchristianlyrics.data.local.entity.SongEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongsDataSource @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    suspend fun fetchSongs(): List<SongEntity> = withContext(Dispatchers.IO) {
        context.assets.open("songs.json").bufferedReader().use { reader ->
            val type = object : TypeToken<List<SongEntity>>() {}.type
            requireNotNull(gson.fromJson<List<SongEntity>>(reader, type)) {
                "The bundled songs catalog is invalid."
            }
        }
    }
}