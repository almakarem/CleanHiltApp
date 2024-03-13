package com.example.cleanhiltapp.data.repository

import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.data.remote.ChatApiService
import com.example.cleanhiltapp.data.remote.CoinPaprikaApi
import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDetailDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedAnswerDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.ChatRequestBody
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import com.example.cleanhiltapp.domain.model.GeneratedAnswer
import com.example.cleanhiltapp.domain.repository.ChatRepository
import com.example.cleanhiltapp.domain.repository.CoinRepository
import okhttp3.RequestBody
import javax.inject.Inject

class ChatRepositoryImpl  @Inject constructor(
    private val api: ChatApiService
) : ChatRepository {
    override suspend fun getPrompt(message: ChatRequestBody): GeneratedAnswerDto {
        return api.getPrompt(message)
    }


}