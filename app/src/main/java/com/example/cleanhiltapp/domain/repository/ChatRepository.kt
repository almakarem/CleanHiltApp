package com.example.cleanhiltapp.domain.repository

import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.data.remote.ChatApiService
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedAnswerDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.ChatRequestBody
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import com.example.cleanhiltapp.domain.model.GeneratedAnswer
import okhttp3.RequestBody

interface ChatRepository {
    suspend fun getPrompt(message: ChatRequestBody): GeneratedAnswerDto
}