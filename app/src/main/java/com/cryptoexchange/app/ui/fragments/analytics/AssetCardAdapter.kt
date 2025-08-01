package com.cryptoexchange.app.ui.fragments.analytics

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cryptoexchange.app.R
import com.cryptoexchange.app.data.AssetCard

class AssetCardAdapter(private val assets: List<AssetCard>) :
    RecyclerView.Adapter<AssetCardAdapter.AssetViewHolder>() {

    inner class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCoin: ImageView = itemView.findViewById(R.id.ivCoin)
        val tvCoinName: TextView = itemView.findViewById(R.id.tvCoinName)
        val tvCoinValue: TextView = itemView.findViewById(R.id.tvCoinPrice)
        val tvCoinDelta: TextView = itemView.findViewById(R.id.tvCoinDelta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_asset_card, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val asset = assets[position]
        holder.imgCoin.setImageResource(asset.imageResId)
        holder.tvCoinName.text = asset.name
        holder.tvCoinValue.text = asset.value
        holder.tvCoinDelta.text = asset.delta
        holder.tvCoinDelta.setTextColor(
            if (asset.isPositive)
                Color.parseColor("#4CAF50") // green
            else
                Color.parseColor("#F44336") // red
        )
    }

    override fun getItemCount(): Int = assets.size
}
