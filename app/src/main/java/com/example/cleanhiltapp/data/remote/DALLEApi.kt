package com.example.cleanhiltapp.data.remote

import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DALLEApi {
    @POST("v1/images/generations")
    suspend fun getPrompt(@Body body: DALLERequestBody) : GeneratedImage

    @Multipart
    @POST("v1/images/edits")
    suspend fun editImage(
        @Part image: MultipartBody.Part,
        @Part("n") n: RequestBody,
        @Part("prompt") prompt: RequestBody,
        @Part("size") size: RequestBody
    ): Response<GeneratedImage>


}