package com.example.cleanhiltapp.presentation.ChatGPTResponse.components

import com.example.cleanhiltapp.domain.model.CoinDetail
import com.example.cleanhiltapp.domain.model.GeneratedAnswer

data class ResponseState(
    val isLoading: Boolean = false,
    val response: GeneratedAnswer? = null,
    val error: String = ""
)
