package pnj.uas.ti.michael_natanael

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailNewsActivity() : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val imageBerita = findViewById<ImageView>(R.id.image_berita)
        val textJudulBerita = findViewById<TextView>(R.id.text_judul_berita)
        val textDeskripsiBerita = findViewById<TextView>(R.id.text_deskripsi_berita)
        val backButton = findViewById<Button>(R.id.back_button)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        Glide.with(this).load(sharedPreferences.getString("imageBerita", "")).into(imageBerita)
        textJudulBerita.text = sharedPreferences.getString("textJudulBerita", "")
        textDeskripsiBerita.text = sharedPreferences.getString("textDeskripsiBerita", "")

        backButton.setOnClickListener {
            // Going back to the previous activity
            finish()
        }
    }

//    private fun navigateToHome() {
//        val intent = Intent(this, HomeActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

}
