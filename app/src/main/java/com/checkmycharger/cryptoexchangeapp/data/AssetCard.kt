package com.checkmycharger.cryptoexchangeapp.data

data class AssetCard(
    val imageResId: Int,
    val name: String,
    val value: String,
    val delta: String,
    val isPositive: Boolean
)
