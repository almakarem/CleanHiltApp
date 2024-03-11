package com.example.cleanhiltapp.domain.use_case.get_coin

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.data.remote.data_transfer_object.toCoin
import com.example.cleanhiltapp.data.remote.data_transfer_object.toCoinDetail
import com.example.cleanhiltapp.domain.model.Coin
import com.example.cleanhiltapp.domain.model.CoinDetail
import com.example.cleanhiltapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured."))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "couldn't reach server. Check your internet connection"))
        }
    }
}