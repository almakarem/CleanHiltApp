package com.example.cleanhiltapp.presentation.coin_list

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.domain.use_case.get_coins.GetCoinsUseCase
import com.example.cleanhiltapp.presentation.coin_list.components.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    init {
        getCoins()
    }

    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getCoins(){

        getCoinsUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }
                is Resource.Error ->{
                    _state.value = CoinListState(error = result.message ?:
                    "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}