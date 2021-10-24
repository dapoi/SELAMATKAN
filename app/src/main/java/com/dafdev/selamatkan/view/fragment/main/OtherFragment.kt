package com.dafdev.selamatkan.view.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dafdev.selamatkan.databinding.FragmentOtherBinding
import com.dafdev.selamatkan.view.activity.auth.OnboardingActivity
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.auth.FirebaseAuth

class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        binding.test.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
        }
    }

}