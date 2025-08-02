package com.cryptoexchange.app.data

import com.cryptoexchange.app.R

data class ChipInfo(
    val chipId: Int,
    val date: String,
    val price: String
)

val chipInfoList = listOf(
    ChipInfo(R.id.chip1h, "20 Feb 2025", "₹ 1,23,224"),
    ChipInfo(R.id.chip8h, "20 Feb 2025", "₹ 1,21,108"),
    ChipInfo(R.id.chip1d, "19 Feb 2025", "₹ 1,18,775"),
    ChipInfo(R.id.chip1w, "13 Feb 2025", "₹ 1,14,600"),
    ChipInfo(R.id.chip1m, "20 Jan 2025", "₹ 1,10,408"),
    ChipInfo(R.id.chip6m, "20 Feb 2024", "₹ 98,200"),
    ChipInfo(R.id.chip1y, "20 Feb 2023", "₹ 87,440")
)
