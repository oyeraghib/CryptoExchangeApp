package com.cryptoexchange.app.ui.fragments.analytics

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryptoexchange.app.R
import com.cryptoexchange.app.data.AssetCard
import com.cryptoexchange.app.data.RecentTransactionCard
import com.cryptoexchange.app.data.chipInfoList
import com.cryptoexchange.app.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.chip.Chip

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private var selectedType: String = "money"

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
        setupLineChart(binding.portfolioLineChart, requireContext())

        //setup recycler views
        setupAssetRecyclerView()
        setupRecentTransactions()

        //on click listeners
        setupOnClickListeners()

        // Set default to "money"
        selectButton(binding.btnMoney, listOf(binding.btnBTC))
        selectedType = "money"

        //set default graph value
        val defaultChipId = R.id.chip1m
        binding.timeChipGroup.check(defaultChipId)

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                highlightChip(defaultChipId)
            }
        })

    }

    private fun setupOnClickListeners() {
        binding.btnBTC.setOnClickListener {
            if (selectedType != "btc") {
                selectButton(binding.btnBTC, listOf(binding.btnMoney))
                selectedType = "btc"
                handleSelectionChange(selectedType)
            }
        }

        binding.btnMoney.setOnClickListener {
            if (selectedType != "money") {
                selectButton(binding.btnMoney, listOf(binding.btnBTC))
                selectedType = "money"
                handleSelectionChange(selectedType)
            }
        }

        binding.timeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            highlightChip(checkedId)
        }
    }

    private fun highlightChip(checkedId: Int) {
        val chip = binding.timeChipGroup.findViewById<Chip>(checkedId) ?: return

        val chipLocation = IntArray(2)
        chip.getLocationOnScreen(chipLocation)

        val chartLocation = IntArray(2)
        binding.chartContainer.getLocationOnScreen(chartLocation)

        val chipCenterX = chipLocation[0] + chip.width / 2
        val relativeX = chipCenterX - chartLocation[0]

        // Show vertical line
        binding.chartHighlightLine.apply {
            visibility = View.VISIBLE
            translationX = relativeX.toFloat()
        }

        binding.chartHighlightDot.apply {
            visibility = View.VISIBLE
            translationX = relativeX - width / 2f
        }


        // Get chip-specific data
        val chipInfo = chipInfoList.find { it.chipId == checkedId } ?: return
        binding.tvDate.text = chipInfo.date
        binding.tvPrice.text = chipInfo.price
        binding.chartLabelContainer.visibility = View.VISIBLE

        binding.chartLabelContainer.post {
            val labelWidth = binding.chartLabelContainer.width
            val showRight = checkedId in listOf(R.id.chip1h, R.id.chip8h, R.id.chip1d)
            val offset = 12

            val labelX = if (showRight) {
                relativeX + offset
            } else {
                relativeX - labelWidth - offset
            }

            binding.chartLabelContainer.translationX = labelX.toFloat()
            binding.chartLabelContainer.translationY = 12f
        }
    }


    private fun handleSelectionChange(selection: String) {
        when (selection) {
            "money" -> {
                binding.tvValue.text = "₹2,37,402.09"
            }
            "btc" -> {
                binding.tvValue.text = "0.0791 BTC"
            }
        }
    }

    private fun selectButton(selected: ImageButton, others: List<ImageButton>) {
        selected.setBackgroundResource(R.drawable.bg_selector_selected)
        others.forEach {
            it.setBackgroundResource(R.drawable.bg_selector_unselected)
        }
    }


    private fun setupLineChart(chart: LineChart, context: Context) {
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
            color = Color.parseColor("#00FF99")  // Mint green
            setDrawCircles(false)
            setDrawValues(false)
            lineWidth = 2f
            mode = LineDataSet.Mode.CUBIC_BEZIER

            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.chart_gradient)
        }

        chart.data = LineData(dataSet)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setTouchEnabled(false)
        chart.setScaleEnabled(false)
        chart.axisRight.isEnabled = false
        chart.xAxis.isEnabled = false

        chart.axisLeft.apply {
//            textColor = Color.WHITE
//            gridColor = Color.TRANSPARENT
//            setDrawAxisLine(false)
//            setDrawGridLines(false)
            isEnabled = false
        }

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
            RecentTransactionCard(R.drawable.ic_btc_png, "Sent", "Jul 25, 2025", "BTC", "0.00045"),
            RecentTransactionCard(R.drawable.ic_eth_png, "Received", "Jul 24, 2025", "ETH", "0.224"),
            RecentTransactionCard(R.drawable.ic_doge_png, "Received", "Jul 23, 2025", "DOGE", "120.00"),
            RecentTransactionCard(R.drawable.ic_solana_png, "Sent", "Jul 21, 2025", "SOL", "0.5001"),
            RecentTransactionCard(R.drawable.ic_btc_png, "Sent", "Jul 20, 2025", "BTC", "0.0012")
        )

        val adapter = RecentTransactionAdapter(mockTransactions, false)
        binding.rvRecentTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecentTransactions.adapter = adapter
        binding.rvRecentTransactions.setHasFixedSize(false)
        binding.rvRecentTransactions.isNestedScrollingEnabled = false

    }


}