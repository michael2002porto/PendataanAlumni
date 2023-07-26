package pnj.uts.ti.michael_natanael.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import pnj.uts.ti.michael_natanael.HomeActivity
import pnj.uts.ti.michael_natanael.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvSignin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val email: String = binding.txtEmail.text.toString()
            val password: String = binding.txtPass.text.toString()
            val confirm_pass: String = binding.txtPassConfirm.text.toString()

            signup_firebase(email, password, confirm_pass)
        }
    }

    private fun signup_firebase(email: String, password: String, confirm_pass: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && confirm_pass.isNotEmpty()) {
            if (password == confirm_pass) {
                // Pendaftaran user (Sign Up)
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Pindah ke halaman apa ?
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this, "Samakan Password dan Konfirmasi Password", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, "Lengkapi Input untuk Sign Up", Toast.LENGTH_SHORT)
                .show()
        }
    }
}