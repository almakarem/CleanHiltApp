package com.example.cleanhiltapp.data.remote.data_transfer_object

import com.example.cleanhiltapp.domain.model.CoinDetail
import com.example.cleanhiltapp.domain.model.GeneratedAnswer

data class GeneratedAnswerDto(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)

