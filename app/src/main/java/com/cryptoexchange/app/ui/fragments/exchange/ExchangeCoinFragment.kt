package com.cryptoexchange.app.ui.fragments.exchange

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cryptoexchange.app.R
import com.cryptoexchange.app.data.CoinInfo
import com.cryptoexchange.app.data.coinMap
import com.cryptoexchange.app.databinding.FragmentExchangeCoinBinding
import com.cryptoexchange.app.databinding.LayoutCardForCoinExchangeBinding

class ExchangeCoinFragment : Fragment() {

    private var _binding: FragmentExchangeCoinBinding? = null
    private val binding get() = _binding!!

    private lateinit var topCardBinding: LayoutCardForCoinExchangeBinding
    private lateinit var bottomCardBinding: LayoutCardForCoinExchangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExchangeCoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCardCorners()
        val topCard = binding.cardContainer.getChildAt(0)
        val bottomCard = binding.cardContainer.getChildAt(1)

        topCardBinding = LayoutCardForCoinExchangeBinding.bind(topCard)
        bottomCardBinding = LayoutCardForCoinExchangeBinding.bind(bottomCard)

        //initial value
        setCardData(topCardBinding, coinMap["ETH"]!!)
        setCardData(bottomCardBinding, coinMap["INR"]!!)

        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.ivSwitchCoins.setOnClickListener {
            val topCoin = topCardBinding.tvCoinNameSend.text.toString()
            val bottomCoin = bottomCardBinding.tvCoinNameSend.text.toString()

            val topCoinInfo = coinMap[topCoin]
            val bottomCoinInfo = coinMap[bottomCoin]

            topCoinInfo?.let { setCardData(topCardBinding, bottomCoinInfo!!) }
            bottomCoinInfo?.let { setCardData(bottomCardBinding, topCoinInfo!!) }
        }

        topCardBinding.ivArrowDownSpinner.setOnClickListener {
            showCoinPicker(it.context) { selectedCoin ->
                coinMap[selectedCoin]?.let {
                    setCardData(topCardBinding, it)
                }
            }
        }

        bottomCardBinding.ivArrowDownSpinner.setOnClickListener {
            showCoinPicker(it.context) { selectedCoin ->
                coinMap[selectedCoin]?.let {
                    setCardData(bottomCardBinding, it)
                }
            }
        }
    }

    private fun showCoinPicker(context: Context, onSelected: (String) -> Unit) {
        val coins = listOf("BTC", "ETH", "INR")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Coin")
        builder.setItems(coins.toTypedArray()) { _, which ->
            onSelected(coins[which])
        }
        builder.show()
    }


    /**
     * Helps setting up the card data
     */
    private fun setCardData(binding: LayoutCardForCoinExchangeBinding, coin: CoinInfo) {
        binding.tvCoinNameSend.text = coin.name
        binding.tvAmountSend.text = coin.amount
        binding.tvBalanceSend.text = coin.balance

        binding.ivCoinIconSend.setImageResource(coin.iconResId)

        if (coin.name == "INR") {
            binding.ivCoinIconSend.setBackgroundResource(R.drawable.bg_inr_circle)
            binding.ivCoinIconSend.setPadding(9, 9, 9, 9)
            binding.ivCoinIconSend.scaleType = ImageView.ScaleType.CENTER_INSIDE
        } else {
            binding.ivCoinIconSend.setBackgroundColor(Color.TRANSPARENT)
            binding.ivCoinIconSend.setPadding(0, 0, 0, 0)
            binding.ivCoinIconSend.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    /**
     * Set up the card corners dynamically. The top card have upper round corners and the bottom
     * card have lower rounded corners
     */
    private fun setupCardCorners() {
        val topCard = binding.cardContainer.getChildAt(0)

        val bottomCard = binding.cardContainer.getChildAt(1)

        topCard.findViewById<View>(R.id.cardRoot).background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_card_top_round)

        bottomCard.findViewById<View>(R.id.cardRoot).background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_card_bottom_round)

    }

}