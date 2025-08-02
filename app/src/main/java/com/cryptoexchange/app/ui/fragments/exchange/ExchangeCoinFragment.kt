package com.cryptoexchange.app.ui.fragments.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cryptoexchange.app.R
import com.cryptoexchange.app.databinding.FragmentExchangeCoinBinding

class ExchangeCoinFragment : Fragment() {

    private var _binding: FragmentExchangeCoinBinding? = null
    private val binding get() = _binding!!

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