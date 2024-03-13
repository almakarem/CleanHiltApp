package com.example.cleanhiltapp.domain.model

data class DALLERequestBody (

    val model: String,
    val prompt: String,
    val size: String,
    val n: Int
)
