package com.example.cleanhiltapp.domain.use_case.ImageToImage

import android.content.Context
import android.net.Uri
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.DALLEImageRequestBody
import com.example.cleanhiltapp.domain.repository.DALLERepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject

class GetImageFromImageUseCase @Inject constructor(
    private val repository: DALLERepository,
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(
        image: MultipartBody.Part,
        n: Int,
        prompt: String,
        size: String
    ): Flow<Resource<Response<GeneratedImage>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.editImage(image,n,prompt,size)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}