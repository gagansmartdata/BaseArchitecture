package com.sdm.mediacard.domain.model

data class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String?,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean
)
