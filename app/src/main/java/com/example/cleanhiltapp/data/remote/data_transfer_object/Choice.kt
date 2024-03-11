package com.example.cleanhiltapp.data.remote.data_transfer_object

data class Choice(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val text: String
)