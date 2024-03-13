package com.example.cleanhiltapp.presentation.Image

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanhiltapp.common.Constants
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedAnswerDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.ChatRequestBody
import com.example.cleanhiltapp.domain.model.DALLEImageRequestBody
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import com.example.cleanhiltapp.domain.use_case.ImageToImage.GetImageFromImageUseCase
import com.example.cleanhiltapp.domain.use_case.TextToImage.GetImageFromTextUseCase
import com.example.cleanhiltapp.domain.use_case.get_coin.GetCoinUseCase
import com.example.cleanhiltapp.domain.use_case.get_response.GetChatResponseUseCase
import com.example.cleanhiltapp.presentation.coin_detail.components.CoinDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class ToImageViewModel @Inject constructor(
    private val getImageFromTextUseCase: GetImageFromTextUseCase,
    private val getImageFromImageUseCase: GetImageFromImageUseCase
) : ViewModel() {

    // StateFlow approach
    private val _chatResponse = MutableStateFlow<Resource<GeneratedImage>>(Resource.Loading())
    val chatResponse: StateFlow<Resource<GeneratedImage>> = _chatResponse.asStateFlow()

    private val _chatImageResponse = MutableStateFlow<Resource<Response<GeneratedImage>>>(Resource.Loading())
    val chatImageResponse: StateFlow<Resource<Response<GeneratedImage>>> = _chatImageResponse.asStateFlow()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun sendMessage(body: DALLERequestBody) {
        viewModelScope.launch {
            getImageFromTextUseCase(body).collect { resource ->
                _chatResponse.value = resource
            }
        }
    }

    fun editImage(image: MultipartBody.Part,
                  n: Int,
                  prompt: String,
                  size: String
    ){
        viewModelScope.launch {
            getImageFromImageUseCase(image,n,prompt,size).collect { resource ->
                _chatImageResponse.value = resource
            }
        }
    }

}