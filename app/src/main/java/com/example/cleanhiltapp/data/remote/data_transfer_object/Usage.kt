package com.example.cleanhiltapp.data.remote.data_transfer_object

data class Usage(
    val completion_tokens: Int,
    val prompt_tokens: Int,
    val total_tokens: Int
)