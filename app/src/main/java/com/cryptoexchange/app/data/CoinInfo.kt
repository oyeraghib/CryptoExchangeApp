package com.cryptoexchange.app.data

import com.cryptoexchange.app.R

data class CoinInfo(
    val name: String,
    val iconResId: Int,
    val balance: String,
    val amount: String
)

val coinMap = mapOf(
    "ETH" to CoinInfo("ETH", R.drawable.ic_eth_png, "0.542", "1.5"),
    "BTC" to CoinInfo("BTC", R.drawable.ic_btc_png, "0.125", "2.424"),
    "INR" to CoinInfo("INR", R.drawable.ic_inr_currency, "₹21,230", "3,53,135.07")
)

