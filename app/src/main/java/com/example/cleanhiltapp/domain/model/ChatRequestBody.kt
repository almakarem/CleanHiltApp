package com.example.cleanhiltapp.domain.model

data class ChatRequestBody(
    val model: String,
    val prompt: String,
    val maxTokens: Int,
    val temperature: Float,
    val topP: Float,
    val frequencyPenalty: Int,
    val presencePenalty: Int,
    val stopSequences: String
)
