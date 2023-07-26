package pnj.uts.ti.michael_natanael

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class TambahDataActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var tanggalLahirEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)

        dbHelper = DatabaseHelper(this)
        db = dbHelper.writableDatabase

        val nimEditText = findViewById<EditText>(R.id.nim_edit_text)
        val namaAlumniEditText = findViewById<EditText>(R.id.nama_alumni_edit_text)
        val tempatLahirEditText = findViewById<EditText>(R.id.tempat_lahir_edit_text)
        tanggalLahirEditText = findViewById(R.id.tanggal_lahir_edit_text)
        val alamatEditText = findViewById<EditText>(R.id.alamat_edit_text)
        val agamaEditText = findViewById<EditText>(R.id.agama_edit_text)
        val teleponEditText = findViewById<EditText>(R.id.telepon_edit_text)
        val tahunMasukEditText = findViewById<EditText>(R.id.tahun_masuk_edit_text)
        val tahunLulusEditText = findViewById<EditText>(R.id.tahun_lulus_edit_text)
        val pekerjaanEditText = findViewById<EditText>(R.id.pekerjaan_edit_text)
        val jabatanEditText = findViewById<EditText>(R.id.jabatan_edit_text)
        val simpanButton = findViewById<Button>(R.id.simpan_button)
        val backButton = findViewById<Button>(R.id.back_button)

        tanggalLahirEditText.setOnClickListener {
            showDatePicker()
        }

        simpanButton.setOnClickListener {
            val nim = nimEditText.text.toString()
            val namaAlumni = namaAlumniEditText.text.toString()
            val tempatLahir = tempatLahirEditText.text.toString()
            val alamat = alamatEditText.text.toString()
            val agama = agamaEditText.text.toString()
            val telepon = teleponEditText.text.toString()
            val tahunMasuk = tahunMasukEditText.text.toString()
            val tahunLulus = tahunLulusEditText.text.toString()
            val pekerjaan = pekerjaanEditText.text.toString()
            val jabatan = jabatanEditText.text.toString()

            if (nim.isNotEmpty() && namaAlumni.isNotEmpty() && tempatLahir.isNotEmpty()
                && alamat.isNotEmpty() && agama.isNotEmpty() && telepon.isNotEmpty()
                && tahunMasuk.isNotEmpty() && tahunLulus.isNotEmpty()
            ) {
                val values = ContentValues()
                values.put(DatabaseContract.DataEntry.COLUMN_NIM, nim)
                values.put(DatabaseContract.DataEntry.COLUMN_NAMA_ALUMNI, namaAlumni)
                values.put(DatabaseContract.DataEntry.COLUMN_TEMPAT_LAHIR, tempatLahir)
                values.put(
                    DatabaseContract.DataEntry.COLUMN_TANGGAL_LAHIR,
                    tanggalLahirEditText.text.toString()
                )
                values.put(DatabaseContract.DataEntry.COLUMN_ALAMAT, alamat)
                values.put(DatabaseContract.DataEntry.COLUMN_AGAMA, agama)
                values.put(DatabaseContract.DataEntry.COLUMN_TELEPON, telepon)
                values.put(DatabaseContract.DataEntry.COLUMN_TAHUN_MASUK, tahunMasuk)
                values.put(DatabaseContract.DataEntry.COLUMN_TAHUN_LULUS, tahunLulus)
                values.put(DatabaseContract.DataEntry.COLUMN_PEKERJAAN, pekerjaan)
                values.put(DatabaseContract.DataEntry.COLUMN_JABATAN, jabatan)

                db.insert(DatabaseContract.DataEntry.TABLE_NAME, null, values)

                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()

                navigateToDataAlumni()
            } else {
                Toast.makeText(this, "Mohon lengkapi semua field", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            navigateToHome()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, this, year, month, day)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)

        tanggalLahirEditText.setText(formattedDate)
    }

    private fun navigateToDataAlumni() {
        val intent = Intent(this, DataAlumniActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

object DatabaseContract {
    object DataEntry {
        const val TABLE_NAME = "alumni"
        const val COLUMN_NIM = "nim"
        const val COLUMN_NAMA_ALUMNI = "nama_alumni"
        const val COLUMN_TEMPAT_LAHIR = "tempat_lahir"
        const val COLUMN_TANGGAL_LAHIR = "tanggal_lahir"
        const val COLUMN_ALAMAT = "alamat"
        const val COLUMN_AGAMA = "agama"
        const val COLUMN_TELEPON = "telepon"
        const val COLUMN_TAHUN_MASUK = "tahun_masuk"
        const val COLUMN_TAHUN_LULUS = "tahun_lulus"
        const val COLUMN_PEKERJAAN = "pekerjaan"
        const val COLUMN_JABATAN = "jabatan"
    }
}

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "alumni.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES = """
            CREATE TABLE ${DatabaseContract.DataEntry.TABLE_NAME} (
                ${DatabaseContract.DataEntry.COLUMN_NIM} TEXT PRIMARY KEY,
                ${DatabaseContract.DataEntry.COLUMN_NAMA_ALUMNI} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_TEMPAT_LAHIR} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_TANGGAL_LAHIR} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_ALAMAT} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_AGAMA} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_TELEPON} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_TAHUN_MASUK} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_TAHUN_LULUS} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_PEKERJAAN} TEXT,
                ${DatabaseContract.DataEntry.COLUMN_JABATAN} TEXT
            )
        """
        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${DatabaseContract.DataEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}
