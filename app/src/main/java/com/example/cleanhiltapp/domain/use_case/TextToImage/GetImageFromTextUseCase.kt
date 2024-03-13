package com.example.cleanhiltapp.domain.use_case.TextToImage

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedAnswerDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.data.remote.data_transfer_object.toCoin
import com.example.cleanhiltapp.data.remote.data_transfer_object.toCoinDetail
import com.example.cleanhiltapp.domain.model.ChatRequestBody
import com.example.cleanhiltapp.domain.model.Coin
import com.example.cleanhiltapp.domain.model.CoinDetail
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import com.example.cleanhiltapp.domain.model.GeneratedAnswer
import com.example.cleanhiltapp.domain.repository.ChatRepository
import com.example.cleanhiltapp.domain.repository.CoinRepository
import com.example.cleanhiltapp.domain.repository.DALLERepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import java.io.IOException
import javax.inject.Inject

class GetImageFromTextUseCase @Inject constructor(
    private val repository: DALLERepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(body: DALLERequestBody): Flow<Resource<GeneratedImage>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getPrompt(body)
            emit(Resource.Success(response))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured."))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "couldn't reach server. Check your internet connection"))
        }
    }
}