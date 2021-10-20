package com.dafdev.selamatkan.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    private var userID: String? = null
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
                        inputEmail.error = "Kamu belum memasukkan email"
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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Pendaftaran berhasil, periksa email anda", Toast.LENGTH_LONG)
                    .show()
                userID = mAuth.currentUser?.uid
                val user = hashMapOf(
                    "name" to name
                )
                fStore.collection("users")
                    .add(user)
                    .addOnSuccessListener { doc ->
                        Log.d("Register User", "Document added with ID: ${doc.id}")
                    }
                    .addOnFailureListener { exception ->
                        Log.w("Register User", "Error adding document ${exception.message}")
                    }
                startActivity(Intent(this, LogInActivity::class.java))
            } else {
                Toast.makeText(
                    this,
                    "Pendaftaran gagal, silahkan coba lagi",
                    Toast.LENGTH_LONG
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