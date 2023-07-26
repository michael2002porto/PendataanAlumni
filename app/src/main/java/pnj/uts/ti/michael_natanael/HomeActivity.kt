package pnj.uts.ti.michael_natanael

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import pnj.uts.ti.michael_natanael.auth.SignInActivity
import pnj.uts.ti.michael_natanael.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val topNavView: MaterialToolbar = binding.topNavView
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_news, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        topNavView.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_tambah_data -> {
                    navigateToTambahData()
                    true
                }
                R.id.menu_data_alumni -> {
                    navigateToDataAlumni()
                    true
                }
                R.id.menu_logout -> {
                    showLogoutConfirmationDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToTambahData() {
        val intent = Intent(this, TambahDataActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToDataAlumni() {
        val intent = Intent(this, DataAlumniActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLogoutConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun logout() {
        // Menghapus SharedPreferences
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()

        // Navigasi kembali ke halaman login
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()

        firebaseAuth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser == null) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}