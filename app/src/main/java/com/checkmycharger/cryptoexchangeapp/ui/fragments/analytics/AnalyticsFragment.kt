package com.checkmycharger.cryptoexchangeapp.ui.fragments.analytics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.checkmycharger.cryptoexchangeapp.R
import com.checkmycharger.cryptoexchangeapp.data.AssetCard
import com.checkmycharger.cryptoexchangeapp.data.RecentTransactionCard
import com.checkmycharger.cryptoexchangeapp.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup graph
        setupLineChart(binding.portfolioLineChart)

        //setup recycler views
        setupAssetRecyclerView()
        setupRecentTransactions()

    }

    private fun setupLineChart(chart: LineChart) {
        val entries = listOf(
            Entry(0f, 120f),
            Entry(1f, 130f),
            Entry(2f, 110f),
            Entry(3f, 140f),
            Entry(4f, 142f),
            Entry(5f, 170f),
            Entry(6f, 180f)
        )

        val dataSet = LineDataSet(entries, "Portfolio").apply {
            color = Color.GREEN
            setDrawCircles(false)
            setDrawValues(false)
            lineWidth = 2f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        chart.data = LineData(dataSet)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setTouchEnabled(false)
        chart.setScaleEnabled(false)
        chart.axisRight.isEnabled = false
        chart.xAxis.isEnabled = false
        chart.axisLeft.textColor = Color.WHITE
        chart.axisLeft.gridColor = Color.DKGRAY
        chart.setBackgroundColor(Color.TRANSPARENT)

        chart.invalidate()
    }

    private fun setupAssetRecyclerView() {
        val mockAssets = listOf(
            AssetCard(R.drawable.ic_btc_png, "Bitcoin (BTC)", "₹42,000", "+5.2%", true),
            AssetCard(R.drawable.ic_eth_png, "Ethereum (ETH)", "₹2,800", "-1.3%", false),
            AssetCard(R.drawable.ic_solana_png, "Solana (SOL)", "₹1,050", "+3.8%", true),
            AssetCard(R.drawable.ic_doge_png, "Dogecoin (DOGE)", "₹12.40", "-0.9%", false)
        )

        val adapter = AssetCardAdapter(mockAssets)

        binding.rvAssetCards.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAssetCards.adapter = adapter
    }

    private fun setupRecentTransactions() {
        val mockTransactions = listOf(
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