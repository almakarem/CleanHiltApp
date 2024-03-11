package com.example.cleanhiltapp.data.remote

import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDetailDto
import com.example.cleanhiltapp.data.remote.data_transfer_object.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins() : List<CoinDto>

    @GET("/v`/coins/{coinId}")
    suspend fun getCoinbyId(@Path("coinId") coinId:String) : CoinDetailDto
}