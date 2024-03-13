package com.example.cleanhiltapp.data.repository

import com.example.cleanhiltapp.data.remote.ChatApiService
import com.example.cleanhiltapp.data.remote.DALLEApi
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedAnswerDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.ChatRequestBody
import com.example.cleanhiltapp.domain.model.DALLEImageRequestBody
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import com.example.cleanhiltapp.domain.repository.ChatRepository
import com.example.cleanhiltapp.domain.repository.DALLERepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Part
import java.io.File
import javax.inject.Inject

class DALLERepositroyImpl   @Inject constructor(
    private val api: DALLEApi
) : DALLERepository {
    override suspend fun getPrompt(message: DALLERequestBody): GeneratedImage {
        return api.getPrompt(message)
    }

    override suspend fun editImage(
        image: MultipartBody.Part,
        n: Int,
        prompt: String,
        size: String
    ): Response<GeneratedImage> {
        return api.editImage(image,n,prompt,size)
    }


}