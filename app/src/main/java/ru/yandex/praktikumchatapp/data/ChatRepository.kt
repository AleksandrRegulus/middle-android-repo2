package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {

    companion object {
        private const val DELAY_FACTOR = 2
        private const val DELAY_TIME = 1000L
    }

    fun getReplyMessage(): Flow<String> {
        var currentDelay = DELAY_TIME
        return api.getReply().retryWhen { exception, attempt ->
            delay(currentDelay)
            currentDelay *= DELAY_FACTOR
            true
        }.map {
            currentDelay = DELAY_TIME
            it
        }
    }
}