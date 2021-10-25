package com.dafdev.selamatkan.view.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.databinding.ActivityLogInBinding
import com.dafdev.selamatkan.view.activity.main.HomeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        with(binding) {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            btnLogin.setOnClickListener {
                showControl(false)
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                when {
                    email.isEmpty() -> {
                        inputEmail.error = "Email harus diisi"
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        inputEmail.error = "Email tidak valid"
                        return@setOnClickListener
                    }
                    password.isEmpty() || password.length < 6 -> {
                        inputPassword.error = "Kata sandi harus lebih dari 6 karakter"
                        return@setOnClickListener
                    }
                    else -> {
                        loginUser(email, password)
                        showControl(true)
                    }
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val verif = mAuth.currentUser
                if (verif != null) {
                    when {
                        verif.isEmailVerified -> {
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
                        else -> {
                            showControl(false)
                            verif.sendEmailVerification()
                            Snackbar.make(
                                binding.root,
                                "Cek email anda untuk konfirmasi login",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } else {
                showControl(false)
                Snackbar.make(
                    binding.root,
                    "Email atau kata sandi anda salah",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showControl(state: Boolean) {
        with(binding) {
            if (state) {
                progressBar.visibility = View.VISIBLE
                tvRegister.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                tvRegister.visibility = View.VISIBLE
            }
        }
    }
}