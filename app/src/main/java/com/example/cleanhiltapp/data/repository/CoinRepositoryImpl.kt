package com.example.cleanhiltapp.data.repository

import com.example.cleanhiltapp.data.remote.CoinPaprikaApi
import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDetailDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDto
import com.example.cleanhiltapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository{
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinbyId(coinId)
    }
}