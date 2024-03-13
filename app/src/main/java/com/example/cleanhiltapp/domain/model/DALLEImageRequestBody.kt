package com.example.cleanhiltapp.domain.model

import android.net.Uri

data class DALLEImageRequestBody(
    val image: Uri,
    val model: String,
    val prompt: String,
    val size: String,
    val n: Int
)
