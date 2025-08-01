package com.checkmycharger.cryptoexchangeapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.checkmycharger.cryptoexchangeapp.R
import com.checkmycharger.cryptoexchangeapp.data.RecentTransactionCard
import com.checkmycharger.cryptoexchangeapp.databinding.FragmentExchangeBinding
import com.checkmycharger.cryptoexchangeapp.ui.fragments.analytics.RecentTransactionAdapter

class ExchangeFragment : Fragment() {

    private var _binding: FragmentExchangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecentTransactions()
    }

    private fun setupRecentTransactions() {
        val mockTransactions = listOf(
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 25, 2025", "BTC", "0.00045"),
            RecentTransactionCard(R.drawable.ic_arrow_down, "Received", "Jul 24, 2025", "ETH", "0.224"),
            RecentTransactionCard(R.drawable.ic_arrow_down, "Received", "Jul 23, 2025", "DOGE", "120.00"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 21, 2025", "SOL", "0.5001"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 20, 2025", "BTC", "0.0012"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 25, 2025", "BTC", "0.00045"),
            RecentTransactionCard(R.drawable.ic_arrow_down, "Received", "Jul 24, 2025", "ETH", "0.224"),
            RecentTransactionCard(R.drawable.ic_arrow_down, "Received", "Jul 23, 2025", "DOGE", "120.00"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 21, 2025", "SOL", "0.5001"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 20, 2025", "BTC", "0.0012"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 20, 2025", "BTC", "0.0012"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 25, 2025", "BTC", "0.00045"),
            RecentTransactionCard(R.drawable.ic_arrow_down, "Received", "Jul 24, 2025", "ETH", "0.224"),
            RecentTransactionCard(R.drawable.ic_arrow_down, "Received", "Jul 23, 2025", "DOGE", "120.00"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 21, 2025", "SOL", "0.5001"),
            RecentTransactionCard(R.drawable.ic_arrow_up, "Sent", "Jul 20, 2025", "BTC", "0.0012")
        )

        val adapter = RecentTransactionAdapter(mockTransactions)
        binding.rvRecentTransactions.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean = false
        }
        binding.rvRecentTransactions.adapter = adapter
        binding.rvRecentTransactions.setHasFixedSize(false)

    }
}