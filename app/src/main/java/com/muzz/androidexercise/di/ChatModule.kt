package com.muzz.androidexercise.di

import android.content.Context
import androidx.room.Room
import com.muzz.androidexercise.feature.chat.data.local.ChatDatabase
import com.muzz.androidexercise.feature.chat.data.local.MessageDao
import com.muzz.androidexercise.feature.chat.data.repository.MessageRepository
import com.muzz.androidexercise.feature.chat.data.repository.MessageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    @Singleton
    fun provideChatDatabase(
        @ApplicationContext context: Context,
    ): ChatDatabase =
        Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            "chat.db",
        ).build()

    @Provides
    @Singleton
    fun provideMessageDao(db: ChatDatabase): MessageDao = db.messageDao()

    @Provides
    @Singleton
    fun provideMessageRepository(dao: MessageDao): MessageRepository = MessageRepositoryImpl(dao = dao)
}
