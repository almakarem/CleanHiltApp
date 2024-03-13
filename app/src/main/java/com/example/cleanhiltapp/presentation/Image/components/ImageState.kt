package com.example.cleanhiltapp.presentation.Image.components

import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.CoinDetail

data class ImageState(
    val isLoading: Boolean = false,
    val coin: GeneratedImage? = null,
    val error: String = ""
)
