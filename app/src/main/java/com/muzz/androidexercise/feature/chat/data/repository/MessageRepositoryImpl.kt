package com.muzz.androidexercise.feature.chat.data.repository

import com.muzz.androidexercise.feature.chat.data.local.MessageDao
import com.muzz.androidexercise.feature.chat.data.local.entity.MessageEntity
import com.muzz.androidexercise.feature.chat.domain.model.Message
import com.muzz.androidexercise.feature.chat.domain.model.User
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRepositoryImpl
@Inject
constructor(
    private val dao: MessageDao,
) : MessageRepository {
    // Expose the Room Flow and map entities to domain models
    override fun observeMessages(): Flow<List<Message>> =
        dao.observeMessages().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun sendMessage(
        text: String,
        from: User,
    ) {
        val now = System.currentTimeMillis()
        val entity =
            MessageEntity(
                text = text,
                user = from.name,
                timestampMillis = now,
            )
        dao.insertMessage(entity)
    }

    // Seed a small local conversation matching the design mocks
    // Only runs when the messages table is empty on first launch
    override suspend fun seedIfEmpty() {
        if (dao.getMessageCount() > 0) return

        val base = System.currentTimeMillis() - 2 * ONE_HOUR_MILLIS

        val messages =
            listOf(
                MessageEntity(
                    text = "Wowsa sounds fun",
                    user = User.SARAH.name,
                    timestampMillis = base,
                ),
                MessageEntity(
                    text = "Yeah for sure that works. What time do you think?",
                    user = User.SARAH.name,
                    // > 1 hour after the previous one -> time separator
                    timestampMillis = base + ONE_HOUR_MILLIS + 60_000L,
                ),
                MessageEntity(
                    text =
                        "Does 7pm work for you? I’ve got to go pick up my little brother " +
                                "first from a party",
                    user = User.ME.name,
                    // a few seconds later
                    timestampMillis = base + ONE_HOUR_MILLIS + 60_000L + 5_000L,
                ),
                MessageEntity(
                    text = "Ok cool!",
                    user = User.SARAH.name,
                    timestampMillis = base + ONE_HOUR_MILLIS + 60_000L + 25_000L,
                ),
                MessageEntity(
                    text = "What are you up to today?",
                    user = User.ME.name,
                    timestampMillis = base + ONE_HOUR_MILLIS + 60_000L + 35_000L,
                ),
                MessageEntity(
                    text = "Nothing much",
                    user = User.SARAH.name,
                    timestampMillis = base + ONE_HOUR_MILLIS + 60_000L + 45_000L,
                ),
                MessageEntity(
                    text =
                        "Actually just about to go shopping, got any recommendations for " +
                                "a good shoe shop? I’m a fashion disaster",
                    user = User.SARAH.name,
                    // < 20 seconds after previous -> compact spacing between these two
                    timestampMillis = base + ONE_HOUR_MILLIS + 60_000L + 60_000L,
                ),
                MessageEntity(
                    text = "The last one went on for hours",
                    user = User.SARAH.name,
                    // >= 20 seconds after previous -> new group
                    timestampMillis = base + ONE_HOUR_MILLIS + 60_000L + 80_000L,
                ),
            )
        dao.insertMessages(messages)
    }

    private fun MessageEntity.toDomain(): Message =
        Message(
            id = id,
            text = text,
            user = if (user == User.ME.name) User.ME else User.SARAH,
            timestampMillis = timestampMillis,
        )

    companion object {
        private const val ONE_HOUR_MILLIS = 60 * 60 * 1000L
    }
}
