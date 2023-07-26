package pnj.uts.ti.michael_natanael

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val emailInput = findViewById<EditText>(R.id.email_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (isValidCredentials(email, password)) {
                // Simpan data ke SharedPreferences
                saveUserData(email)

                // Navigasi ke halaman profil
                navigateToHome()
            } else {
                showInvalidCredentialsDialog()
            }
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        val hardcodedEmail = "michael.natanael.tik21@mhsw.pnj.ac.id"
        val hardcodedPassword = "pnj_mendunia"

        return email == hardcodedEmail && password == hardcodedPassword
    }

    private fun saveUserData(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("nim", "2107411002")
        editor.putString("nama", "Michael Natanael")
        editor.putString("kelas", "TI 4A")
        editor.apply()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showInvalidCredentialsDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Invalid Credentials")
            .setMessage("The email or password you entered is invalid.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = dialogBuilder.create()
        dialog.show()
    }
}
