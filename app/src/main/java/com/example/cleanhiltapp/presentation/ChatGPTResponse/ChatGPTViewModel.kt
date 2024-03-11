package com.example.cleanhiltapp.presentation.ChatGPTResponse

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.domain.model.ChatRequestBody
import com.example.cleanhiltapp.domain.model.GeneratedAnswer
import com.example.cleanhiltapp.domain.use_case.get_response.GetChatResponseUseCase
import com.example.cleanhiltapp.presentation.ChatGPTResponse.components.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ChatGPTViewModel @Inject constructor(
    private val useCase: GetChatResponseUseCase
) : ViewModel() {

    // StateFlow approach
    private val _chatResponse = MutableStateFlow<Resource<GeneratedAnswer>>(Resource.Loading())
    val chatResponse: StateFlow<Resource<GeneratedAnswer>> = _chatResponse.asStateFlow()

    // Alternatively, using LiveData
    // val chatResponse: LiveData<Resource<GeneratedAnswer>> = liveData {
    //     getChatResponseUseCase(body).collect {
    //         emit(it)
    //     }
    // }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun sendMessage(body: ChatRequestBody) {
        viewModelScope.launch {
            useCase(body).collect { resource ->
                _chatResponse.value = resource
            }
        }
    }

}