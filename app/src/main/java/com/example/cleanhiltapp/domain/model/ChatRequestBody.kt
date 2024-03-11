package com.example.cleanhiltapp.domain.model

data class ChatRequestBody(

        val model: String,
        val messages: List<Message>,
        val temperature: Float,
        val max_tokens: Int,
        val top_p: Float,
        val frequency_penalty: Float,
        val presence_penalty: Float
)
