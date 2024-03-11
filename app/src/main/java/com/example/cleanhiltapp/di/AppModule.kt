package com.example.cleanhiltapp.di

import com.example.cleanhiltapp.common.Constants
import com.example.cleanhiltapp.data.remote.ChatApiService
import com.example.cleanhiltapp.data.remote.CoinPaprikaApi
import com.example.cleanhiltapp.data.repository.ChatRepositoryImpl
import com.example.cleanhiltapp.data.repository.CoinRepositoryImpl
import com.example.cleanhiltapp.domain.repository.ChatRepository
import com.example.cleanhiltapp.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): CoinPaprikaApi{
        return Retrofit.Builder()
            .baseUrl(Constants.CoinPaprika_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi) : CoinRepository{
        return CoinRepositoryImpl(api)
    }

    private const val BASE_URL = "https://api.openai.com/"
    private val token = "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"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    @Provides
    @Singleton
    fun provideApiService(): ChatApiService = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChatApiService::class.java)

    @Provides
    @Singleton
    fun provideChatRepository(api: ChatApiService): ChatRepository = ChatRepositoryImpl(api)

}
