package com.cryptoexchange.app.data

data class RecentTransactionCard(
    val imageResId: Int,
    val type: String,
    val date: String,
    val coinShortName: String,
    val amount: String
)
