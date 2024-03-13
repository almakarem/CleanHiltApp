package com.example.cleanhiltapp.domain.repository

import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.DALLEImageRequestBody
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Part
import java.io.File

interface DALLERepository {
    suspend fun getPrompt(message: DALLERequestBody): GeneratedImage
    suspend fun editImage(
        @Part image: File,
        @Part("n") n: Int,
        @Part("size") size: String
    ): Response<GeneratedImage>
}