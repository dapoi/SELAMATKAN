package com.dafdev.selamatkan.view.fragment.core

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.dafdev.selamatkan.databinding.BmiDialogLayoutBinding

class DialogBMI(
    private val background: Drawable,
    private val image: Drawable,
    private val title: String,
    private val desc: String
) : DialogFragment() {
    private lateinit var binding: BmiDialogLayoutBinding
    private lateinit var dialogResult: Dialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = BmiDialogLayoutBinding.inflate(layoutInflater)
        dialogResult = Dialog(requireContext())
        dialogResult.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogResult.requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCanceledOnTouchOutside(true)
            window?.setLayout(
                (resources.displayMetrics.widthPixels),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        binding.apply {
            bg.setImageDrawable(background)
            imgEmoji.setImageDrawable(image)
            tvTitle.text = title
            tvDesc.text = desc
        }
        return dialogResult
    }

    companion object {
        const val DATA = "data"
    }
}