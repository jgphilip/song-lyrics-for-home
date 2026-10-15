package com.beezoned.bzchristianlyrics.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beezoned.bzchristianlyrics.data.local.entity.SongEntity

@Database(entities = [SongEntity::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}