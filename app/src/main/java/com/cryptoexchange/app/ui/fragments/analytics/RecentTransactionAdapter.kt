package com.cryptoexchange.app.ui.fragments.analytics

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cryptoexchange.app.R
import com.cryptoexchange.app.data.RecentTransactionCard

class RecentTransactionAdapter(
    private val transactions: List<RecentTransactionCard>,
    private val isExchangeTransaction: Boolean
) : RecyclerView.Adapter<RecentTransactionAdapter.TransactionViewHolder>() {

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

        if (isExchangeTransaction) {
            val size = dpToPx(48)
            val padding = dpToPx(12)

            holder.ivTransactionIcon.layoutParams.width = size
            holder.ivTransactionIcon.layoutParams.height = size
            holder.ivTransactionIcon.setPadding(padding, padding, padding, padding)
            holder.ivTransactionIcon.scaleType = ImageView.ScaleType.CENTER_INSIDE
            holder.ivTransactionIcon.setBackgroundResource(R.drawable.bg_circle_gray)
            holder.ivTransactionIcon.requestLayout()
        }
        else {
            val size = dpToPx(48)
            holder.ivTransactionIcon.layoutParams.width = size
            holder.ivTransactionIcon.layoutParams.height = size
            holder.ivTransactionIcon.scaleType = ImageView.ScaleType.FIT_CENTER
            holder.ivTransactionIcon.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            holder.ivTransactionIcon.requestLayout()
        }
    }

    override fun getItemCount(): Int = transactions.size

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}
