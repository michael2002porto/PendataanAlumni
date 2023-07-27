package pnj.uas.ti.michael_natanael

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class DataAlumniActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var backButton: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_alumni)

        listView = findViewById(R.id.alumni_list_view)
        listView = findViewById(R.id.alumni_list_view)
        backButton = findViewById(R.id.back_button)
        dbHelper = DatabaseHelper(this)
        database = dbHelper.readableDatabase

        displayAlumniList()

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedAlumni = parent.getItemAtPosition(position) as Alumni
                val intent = Intent(this, DetailAlumniActivity::class.java)
                intent.putExtra("nim", selectedAlumni.nim)
                startActivity(intent)
            }

        backButton.setOnClickListener {
            navigateToHome()
        }
    }

    private fun displayAlumniList() {
        val alumniList = fetchAlumniData()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alumniList)
        listView.adapter = adapter
    }

    private fun fetchAlumniData(): List<Alumni> {
        val alumniList = mutableListOf<Alumni>()
        val cursor: Cursor = database.query(
            DatabaseContract.DataEntry.TABLE_NAME,
            arrayOf(
                DatabaseContract.DataEntry.COLUMN_NIM,
                DatabaseContract.DataEntry.COLUMN_NAMA_ALUMNI
            ),
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val nim =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_NIM))
            val namaAlumni =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_NAMA_ALUMNI))
            val alumni = Alumni(nim, namaAlumni)
            alumniList.add(alumni)
        }
        cursor.close()
        return alumniList
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

data class Alumni(val nim: String, val nama: String)
