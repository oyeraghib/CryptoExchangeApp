package com.checkmycharger.cryptoexchangeapp.ui.fragments.analytics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.checkmycharger.cryptoexchangeapp.R
import com.checkmycharger.cryptoexchangeapp.data.RecentTransactionCard

class RecentTransactionAdapter(private val transactions: List<RecentTransactionCard>) :
    RecyclerView.Adapter<RecentTransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTransactionIcon: ImageView = itemView.findViewById(R.id.ivTransactionIcon)
        val tvType: TextView = itemView.findViewById(R.id.tvTransactionType)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvCoinShortName: TextView = itemView.findViewById(R.id.tvCoinName)
        val tvAmount: TextView = itemView.findViewById(R.id.tvTransactionAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_recent_transactions, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val txn = transactions[position]
        holder.ivTransactionIcon.setImageResource(txn.imageResId)
        holder.tvType.text = txn.type
        holder.tvDate.text = txn.date
        holder.tvCoinShortName.text = txn.coinShortName
        holder.tvAmount.text = txn.amount
    }

    override fun getItemCount(): Int = transactions.size
}
