package com.muzz.androidexercise.feature.chat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muzz.androidexercise.feature.chat.data.local.entity.MessageEntity

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
