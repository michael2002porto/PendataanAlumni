package pnj.uts.ti.michael_natanael

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailAlumniActivity : AppCompatActivity() {
    private lateinit var nimEditText: EditText
    private lateinit var namaAlumniEditText: EditText
    private lateinit var tempatLahirEditText: EditText
    private lateinit var tanggalLahirEditText: EditText
    private lateinit var alamatEditText: EditText
    private lateinit var agamaEditText: EditText
    private lateinit var teleponEditText: EditText
    private lateinit var tahunMasukEditText: EditText
    private lateinit var tahunLulusEditText: EditText
    private lateinit var pekerjaanEditText: EditText
    private lateinit var jabatanEditText: EditText
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private lateinit var backButton: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var nim: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_alumni)

        nimEditText = findViewById(R.id.nim_edit_text)
        namaAlumniEditText = findViewById(R.id.nama_alumni_edit_text)
        tempatLahirEditText = findViewById(R.id.tempat_lahir_edit_text)
        tanggalLahirEditText = findViewById(R.id.tanggal_lahir_edit_text)
        alamatEditText = findViewById(R.id.alamat_edit_text)
        agamaEditText = findViewById(R.id.agama_edit_text)
        teleponEditText = findViewById(R.id.telepon_edit_text)
        tahunMasukEditText = findViewById(R.id.tahun_masuk_edit_text)
        tahunLulusEditText = findViewById(R.id.tahun_lulus_edit_text)
        pekerjaanEditText = findViewById(R.id.pekerjaan_edit_text)
        jabatanEditText = findViewById(R.id.jabatan_edit_text)
        editButton = findViewById(R.id.edit_button)
        deleteButton = findViewById(R.id.delete_button)
        backButton = findViewById(R.id.back_button)

        dbHelper = DatabaseHelper(this)
        database = dbHelper.writableDatabase

        nim = intent.getStringExtra("nim") ?: ""

        displayAlumniData()

        editButton.setOnClickListener {
            updateAlumniData()
        }

        deleteButton.setOnClickListener {
            deleteAlumniData()
        }

        backButton.setOnClickListener {
            navigateToHome()
        }
    }

    private fun displayAlumniData() {
        val cursor = database.query(
            DatabaseContract.DataEntry.TABLE_NAME,
            null,
            "${DatabaseContract.DataEntry.COLUMN_NIM} = ?",
            arrayOf(nim),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            val namaAlumni =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_NAMA_ALUMNI))
            val tempatLahir =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_TEMPAT_LAHIR))
            val tanggalLahir =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_TANGGAL_LAHIR))
            val alamat =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_ALAMAT))
            val agama =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_AGAMA))
            val telepon =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_TELEPON))
            val tahunMasuk =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_TAHUN_MASUK))
            val tahunLulus =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_TAHUN_LULUS))
            val pekerjaan =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_PEKERJAAN))
            val jabatan =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DataEntry.COLUMN_JABATAN))

            nimEditText.setText(nim)
            namaAlumniEditText.setText(namaAlumni)
            tempatLahirEditText.setText(tempatLahir)
            tanggalLahirEditText.setText(tanggalLahir)
            alamatEditText.setText(alamat)
            agamaEditText.setText(agama)
            teleponEditText.setText(telepon)
            tahunMasukEditText.setText(tahunMasuk)
            tahunLulusEditText.setText(tahunLulus)
            pekerjaanEditText.setText(pekerjaan)
            jabatanEditText.setText(jabatan)
        }
        cursor.close()
    }

    private fun updateAlumniData() {
        val updatedNamaAlumni = namaAlumniEditText.text.toString().trim()
        val updatedTempatLahir = tempatLahirEditText.text.toString().trim()
        val updatedTanggalLahir = tanggalLahirEditText.text.toString().trim()
        val updatedAlamat = alamatEditText.text.toString().trim()
        val updatedAgama = agamaEditText.text.toString().trim()
        val updatedTelepon = teleponEditText.text.toString().trim()
        val updatedTahunMasuk = tahunMasukEditText.text.toString().trim()
        val updatedTahunLulus = tahunLulusEditText.text.toString().trim()
        val updatedPekerjaan = pekerjaanEditText.text.toString().trim()
        val updatedJabatan = jabatanEditText.text.toString().trim()

        val values = ContentValues()
        values.put(DatabaseContract.DataEntry.COLUMN_NAMA_ALUMNI, updatedNamaAlumni)
        values.put(DatabaseContract.DataEntry.COLUMN_TEMPAT_LAHIR, updatedTempatLahir)
        values.put(DatabaseContract.DataEntry.COLUMN_TANGGAL_LAHIR, updatedTanggalLahir)
        values.put(DatabaseContract.DataEntry.COLUMN_ALAMAT, updatedAlamat)
        values.put(DatabaseContract.DataEntry.COLUMN_AGAMA, updatedAgama)
        values.put(DatabaseContract.DataEntry.COLUMN_TELEPON, updatedTelepon)
        values.put(DatabaseContract.DataEntry.COLUMN_TAHUN_MASUK, updatedTahunMasuk)
        values.put(DatabaseContract.DataEntry.COLUMN_TAHUN_LULUS, updatedTahunLulus)
        values.put(DatabaseContract.DataEntry.COLUMN_PEKERJAAN, updatedPekerjaan)
        values.put(DatabaseContract.DataEntry.COLUMN_JABATAN, updatedJabatan)

        val selection = "${DatabaseContract.DataEntry.COLUMN_NIM} = ?"
        val selectionArgs = arrayOf(nim)

        val updatedRows =
            database.update(DatabaseContract.DataEntry.TABLE_NAME, values, selection, selectionArgs)

        if (updatedRows > 0) {
            Toast.makeText(this, "Data alumni berhasil diupdate", Toast.LENGTH_SHORT).show()
            navigateToDataAlumni()
        } else {
            Toast.makeText(this, "Gagal mengupdate data alumni", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteAlumniData() {
        val selection = "${DatabaseContract.DataEntry.COLUMN_NIM} = ?"
        val selectionArgs = arrayOf(nim)

        val deletedRows =
            database.delete(DatabaseContract.DataEntry.TABLE_NAME, selection, selectionArgs)

        if (deletedRows > 0) {
            Toast.makeText(this, "Data alumni berhasil dihapus", Toast.LENGTH_SHORT).show()
            navigateToDataAlumni()
        } else {
            Toast.makeText(this, "Gagal menghapus data alumni", Toast.LENGTH_SHORT).show()
        }
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
