package pnj.uas.ti.michael_natanael.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pnj.uas.ti.michael_natanael.HomeActivity
import pnj.uas.ti.michael_natanael.databinding.ActivitySignInBinding
import java.text.SimpleDateFormat
import java.util.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    private fun isLocationPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignin.setOnClickListener {
            val email: String = binding.txtEmail.text.toString()
            val password: String = binding.txtPass.text.toString()

            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            // Simpan data ke SharedPreferences
            saveUserData(email)

            signin_firebase(email, password)
        }
    }

    private fun saveUserData(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("nama", "Michael Natanael")
        editor.putString("kelas", "TI 4A")
        editor.putString("nim", "2107411002")
        editor.putString("email", email)
        editor.apply()
    }

    @SuppressLint("MissingPermission")
    private fun signin_firebase(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Check if location services are enabled
            if (isLocationEnabled(this)) {
                if (isLocationPermissionGranted()) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let { loc ->
                            val latitude = loc.latitude
                            val longitude = loc.longitude
                            // Use the latitude and longitude values here.
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        // Realtime database (insert success log)
                                        insertLogLoginActivities(email, latitude, longitude, 1)

                                        // Pindah ke halaman apa ?
                                        val intent = Intent(this, HomeActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        // Realtime database (insert failed log)
                                        insertLogLoginActivities(email, latitude, longitude, 0)

                                        // Pesan error
                                        Toast.makeText(
                                            this,
                                            it.exception.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }
                } else {
                    // Location permission is not granted. Request the permission from the user.
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            } else {
                // Location services are not enabled. Ask the user to enable them.
                Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show()
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            Toast.makeText(this, "Lengkapi Input untuk Sign In", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertLogLoginActivities(
        email: String,
        latitude: Double,
        longitude: Double,
        log_login_status: Int
    ) {
        val database = Firebase.database
        val myRef = database.getReference("LogLoginActivities")
        val logId = myRef.push().key
        val log = LogLoginActivities(
            logId,
            email,
            getCurrentDateTimeFormatted(),
            latitude,
            longitude,
            log_login_status
        )
        if (logId != null) {
            myRef.child(logId).setValue(log).addOnCompleteListener {
                Toast.makeText(
                    this, "Log Login Activity Berhasil Ditambahkan", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun getCurrentDateTimeFormatted(): String {
        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDateTime)
    }
}