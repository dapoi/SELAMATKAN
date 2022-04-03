package com.dafdev.selamatkan.view.activity.core

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.ActivityBmiBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.view.fragment.core.DialogBMI
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var _binding: ActivityBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        HelpUtil.setStatusBarColor(this, R.color.white)

        binding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            showControl(false)
            btnCalculate.setOnClickListener {
                when {
                    etWeight.text.isNotEmpty() && etHeight.text.isNotEmpty() -> {
                        val weight = etWeight.text.toString().toDouble()
                        val height = etHeight.text.toString().toDouble() / 100
                        if (weight > 0 && weight < 600 && height >= 0.50 && height < 2.50) {
                            showControl(true)
                            val imm = this@BMIActivity.getSystemService(
                                Context.INPUT_METHOD_SERVICE
                            ) as InputMethodManager
                            imm.hideSoftInputFromWindow(container.windowToken, 0)
                            Handler(Looper.getMainLooper()).postDelayed({
                                val result = calculate(height, weight)
                                setResult(result)
                                showControl(false)
                            }, 1000)
                        } else {
                            val imm = this@BMIActivity.getSystemService(
                                Context.INPUT_METHOD_SERVICE
                            ) as InputMethodManager
                            imm.hideSoftInputFromWindow(container.windowToken, 0)
                            Snackbar.make(
                                binding.root,
                                "Data yang dimasukkan tidak valid",
                                Snackbar.LENGTH_LONG
                            ).also {
                                it.view.translationY = (-200).toFloat()
                                it.setBackgroundTint(
                                    ContextCompat.getColor(
                                        this@BMIActivity,
                                        R.color.orange
                                    )
                                )
                                it.show()
                            }
                        }
                    }
                    etWeight.text.isEmpty() -> {
                        etWeight.error = "Berat badan harus diisi"
                        etWeight.requestFocus()
                    }
                    etHeight.text.isEmpty() -> {
                        etHeight.error = "Tinggi badan harus diisi"
                        etHeight.requestFocus()
                    }
                }
            }
        }
    }

    private fun setResult(result: Double) {
        when {
            result < 18.5 -> setDialog(
                ContextCompat.getDrawable(this, R.drawable.bg_thin)!!,
                ContextCompat.getDrawable(this, R.drawable.img_sad)!!,
                "Beratmu kurang!!",
                "Perbaiki kualitas tidur & pola makan yaa :)"
            )
            result > 18.5 && result < 25.0 -> setDialog(
                ContextCompat.getDrawable(this, R.drawable.bg_ideal)!!,
                ContextCompat.getDrawable(this, R.drawable.img_happy)!!,
                "Tubuhmu Ideal!!",
                "Pertahankan pola hidupmu yaa"
            )
            result > 25.0 -> setDialog(
                ContextCompat.getDrawable(this, R.drawable.bg_fat)!!,
                ContextCompat.getDrawable(this, R.drawable.img_crying)!!,
                "Kamu Kegemukan!!",
                "Kurangi makan gula & jangan lupa diet yaa"
            )
        }
    }

    private fun setDialog(bg: Drawable, img: Drawable, title: String, desc: String) {
        DialogBMI(bg, img, title, desc).show(
            supportFragmentManager, DialogBMI.DATA
        )
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}