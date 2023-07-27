package pnj.uas.ti.michael_natanael.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import pnj.uas.ti.michael_natanael.R
import pnj.uas.ti.michael_natanael.auth.SignInActivity

class ProfileFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var emailTextView: TextView
    private lateinit var nimTextView: TextView
    private lateinit var namaTextView: TextView
    private lateinit var kelasTextView: TextView
    private lateinit var logoutButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        emailTextView = view.findViewById(R.id.text_email)
        nimTextView = view.findViewById(R.id.text_nim)
        namaTextView = view.findViewById(R.id.text_nama)
        kelasTextView = view.findViewById(R.id.text_kelas)
        logoutButton = view.findViewById(R.id.button_logout)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

//        val email = sharedPreferences.getString("email", "")
        val email = firebaseAuth.currentUser?.email.toString()
        val nim = sharedPreferences.getString("nim", "")
        val nama = sharedPreferences.getString("nama", "")
        val kelas = sharedPreferences.getString("kelas", "")

        emailTextView.text = email
        nimTextView.text = nim
        namaTextView.text = nama
        kelasTextView.text = kelas

        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
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
//        val intent = Intent(requireActivity(), MainActivity::class.java)
//        startActivity(intent)

        firebaseAuth.signOut()
        val intent = Intent(requireActivity(), SignInActivity::class.java)
        startActivity(intent)
    }
}
