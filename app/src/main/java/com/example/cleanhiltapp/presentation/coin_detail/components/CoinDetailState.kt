package com.example.cleanhiltapp.presentation.coin_detail.components

import com.example.cleanhiltapp.domain.model.Coin
import com.example.cleanhiltapp.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)
