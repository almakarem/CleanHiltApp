package com.example.cleanhiltapp.domain.model

import com.example.cleanhiltapp.data.remote.data_transfer_object.TeamMember

data class CoinDetail(

    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags:List<String>,
    val team: List<TeamMember>

)
