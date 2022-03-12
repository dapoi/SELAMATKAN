package com.dafdev.selamatkan.view.fragment.main.bottom

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentBmiBinding
import com.dafdev.selamatkan.utils.HelpUtil
import java.math.BigDecimal
import java.math.RoundingMode

class BMIFragment : Fragment() {

    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HelpUtil.setStatusBarWhite(requireActivity(), R.color.white)

        binding.apply {
            showControl(false)
            btnCalculate.setOnClickListener {
                val weight = etWeight.text
                val height = etHeight.text
                when {
                    weight.isEmpty() -> {
                        etWeight.error = "Berat badan harus diisi"
                        etWeight.requestFocus()
                    }
                    height.isEmpty() -> {
                        etHeight.error = "Tinggi badan harus diisi"
                        etHeight.requestFocus()
                    }
                    else -> {
                        val imm = activity!!.getSystemService(
                            Context.INPUT_METHOD_SERVICE
                        ) as InputMethodManager
                        imm.hideSoftInputFromWindow(container.windowToken, 0)
                        showControl(true)
                        btnCalculate.isClickable = false
                        btnCalculate.isEnabled = false
                        calculate(height.toString().toDouble(), weight.toString().toDouble())
                    }
                }
            }
        }
    }

    private fun calculate(height: Double, weight: Double) = BigDecimal(
        weight.div(
            height * height
        )
    ).setScale(2, RoundingMode.HALF_EVEN).toDouble()

    private fun showControl(state: Boolean) {
        with(binding) {
            if (state) {
                progressBar.visibility = View.VISIBLE
                tvCalculate.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                tvCalculate.visibility = View.VISIBLE
            }
        }
    }
}