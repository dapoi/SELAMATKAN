package com.dafdev.selamatkan.view.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        with(binding) {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            btnRegister.setOnClickListener {
                showControl(false)
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                when {
                    name.isEmpty() -> {
                        inputName.error = "Kamu belum memasukkan nama"
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        inputEmail.error = "Email tidak valid"
                        return@setOnClickListener
                    }
                    email.isEmpty() -> {
                        inputEmail.error = "Email harus diisi"
                        return@setOnClickListener
                    }
                    password.isEmpty() || password.length < 6 -> {
                        inputPassword.error = "Kata sandi harus lebih dari 6 karakter"
                        inputPassword.requestFocus()
                        return@setOnClickListener
                    }
                    else -> {
                        registerUser(name, email, password)
                        showControl(true)
                    }
                }
            }
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataUser = hashMapOf(
                    "email" to email,
                    "name" to name
                )
                fStore.collection("users").add(dataUser)
                    .addOnSuccessListener {
                        Timber.i("Register", "Registrasi tersimpan dengan ${it.id}")
                    }
                    .addOnFailureListener {
                        Timber.e("Register", "Registrasi gagal ${it.message}")
                    }
                val verif = mAuth.currentUser
                verif?.sendEmailVerification()
                Toast.makeText(
                    this,
                    "Pendaftaran berhasil, silahkan cek email Anda",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Email ini sudah memiliki akun",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {
            Timber.e("Error", it.message.toString())
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