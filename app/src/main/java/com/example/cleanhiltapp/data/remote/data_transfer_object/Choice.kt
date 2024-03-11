package com.example.cleanhiltapp.data.remote.data_transfer_object

import com.example.cleanhiltapp.domain.model.Message

data class Choice(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val message: Message
)