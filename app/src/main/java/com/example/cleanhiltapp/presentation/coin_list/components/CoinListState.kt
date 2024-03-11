package com.example.cleanhiltapp.presentation.coin_list.components

import com.example.cleanhiltapp.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)
