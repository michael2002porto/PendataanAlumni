package pnj.uts.ti.michael_natanael.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value =
            "Aplikasi Pendataan Alumni adalah sebuah aplikasi yang dirancang untuk memudahkan pengelolaan dan pendataan informasi para alumni suatu institusi atau organisasi. Aplikasi ini membantu dalam mencatat dan menyimpan data-data penting mengenai para alumni, seperti NIM, nama, tempat lahir, tanggal lahir, alamat, agama, nomor telepon, tahun masuk, tahun lulus, pekerjaan, dan jabatan.\n" +
                    "\n" +
                    "Fitur utama dari aplikasi ini meliputi:\n" +
                    "1. Halaman Login: Untuk pengguna masuk ke aplikasi dengan menggunakan email dan password.\n" +
                    "2. Halaman Home: Menampilkan menu-menu utama seperti Tambah Data, Data Alumni, dan Logout.\n" +
                    "3. Halaman Tambah Data: Memungkinkan pengguna untuk menginput data alumni baru dan menyimpannya ke dalam database SQLite. Pada halaman ini, pengguna dapat menggunakan DatePicker untuk memilih tanggal lahir alumni.\n" +
                    "4. Halaman Data Alumni: Menampilkan daftar alumni yang telah terdaftar dalam bentuk ListView. Pengguna dapat melihat daftar ini dan memilih salah satu alumni untuk melihat detailnya.\n" +
                    "5. Halaman Detail Alumni: Menampilkan informasi lengkap mengenai seorang alumni, termasuk NIM, nama, tempat lahir, tanggal lahir, alamat, agama, nomor telepon, tahun masuk, tahun lulus, pekerjaan, dan jabatan. Pada halaman ini, pengguna dapat melakukan edit atau hapus data alumni.\n" +
                    "6. Fitur Logout: Memungkinkan pengguna untuk keluar dari aplikasi dengan menghapus data login yang disimpan.\n" +
                    "\n" +
                    "Aplikasi Pendataan Alumni membantu dalam menyimpan dan mengelola informasi penting mengenai para alumni dengan cara yang efisien dan terorganisir. Dengan aplikasi ini, pengguna dapat dengan mudah mencari dan melihat data alumni, serta melakukan perubahan atau penghapusan jika diperlukan."
    }
    val text: LiveData<String> = _text
}