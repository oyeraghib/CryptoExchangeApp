package com.checkmycharger.cryptoexchangeapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.checkmycharger.cryptoexchangeapp.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.Entry

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

        setupLineChart(binding.portfolioLineChart)

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

}