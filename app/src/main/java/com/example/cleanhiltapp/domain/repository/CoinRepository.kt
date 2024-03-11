package com.example.cleanhiltapp.domain.repository

import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDetailDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}