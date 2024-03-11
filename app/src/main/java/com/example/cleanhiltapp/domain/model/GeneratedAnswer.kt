package com.example.cleanhiltapp.domain.model

import com.example.cleanhiltapp.data.remote.data_transfer_object.Choice
import com.example.cleanhiltapp.data.remote.data_transfer_object.Usage
import com.google.gson.annotations.SerializedName

data class GeneratedAnswer(

    val response: List<String>,

    val created: Int,

    val id: String,

)
