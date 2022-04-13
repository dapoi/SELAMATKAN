package com.dafdev.selamatkan.view.activity.core

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_support -> {
                    val email = "luthfidaffa2202@gmail.com"
                    val subject = "Feedback Aplikasi Selamatkan"
                    val body = "Silahkan tulis pesan anda disini"

                    Intent(Intent.ACTION_SENDTO).let { actionTo ->
                        val urlString =
                            "mailto:${Uri.encode(email)}?subject=${Uri.encode(subject)}&body=${
                                Uri.encode(body)
                            }"
                        actionTo.data = Uri.parse(urlString)

                        Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                            putExtra(Intent.EXTRA_SUBJECT, subject)
                            putExtra(Intent.EXTRA_TEXT, body)
                            selector = actionTo
                            startActivity(Intent.createChooser(this, "Kirim Email"))
                        }
                    }
                }
                else -> {
                    navController.navigate(it.itemId)
                }
            }
            true
        }
    }

    fun openCloseNavigationDrawer(view: View) {
        with(binding) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}