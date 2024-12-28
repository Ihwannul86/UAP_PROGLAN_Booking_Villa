# UJIAN AKHIR PRAKTIKUM PEMROGRAMAN LANJUT 

## Deskripsi

Project VillaBooking adalah aplikasi berbasis Java yang dirancang untuk mempermudah proses pemesanan villa melalui antarmuka pengguna grafis (GUI) menggunakan library Swing. Aplikasi ini menyediakan fitur untuk menampilkan daftar villa berdasarkan kategori (Standar dan Clasic), melacak pemesanan berdasarkan tanggal check-in dan check-out, serta menghitung total biaya sewa secara otomatis. Pengguna dapat melihat detail pesanan mereka melalui tampilan struk yang interaktif, serta menyimpan informasi pemesan seperti nama, jenis kelamin, dan nomor telepon. Selain itu, aplikasi ini memiliki fitur pembayaran dengan validasi jumlah uang yang dimasukkan serta penghitungan kembalian. Semua data transaksi akan dicatat dalam tampilan ringkasan yang dapat diakses melalui jendela terpisah.

## Penjelasan pada Program

Jendela Utama (Villa Booking App)
1. Kategori Pemesanan : Dropdown untuk memilih kategori villa (All, Standar, Clasic).
2. Menu Villa : Menampilkan daftar villa berdasarkan kategori yang dipilih, termasuk gambar, nama, harga, dan field untuk memilih tanggal check-in dan check-out.
3. Bagian Pemesanan : Tombol untuk memesan dan melihat ringkasan. Area teks untuk menampilkan receipt.
Jendela Ringkasan (Summary)
4. Menampilkan tabel yang berisi ringkasan pemesanan, termasuk nama, jenis kelamin, nomor telepon, nama villa, tanggal check-in/check-out, total pembayaran, dan kembalian.
5. Terdapat tombol "Back" untuk kembali ke jendela utama.
Dialog Pembayaran
6. Meminta pengguna untuk memasukkan jumlah pembayaran dan melakukan validasi.
Dialog Input Data Diri
7. Meminta pengguna untuk memasukkan nama, jenis kelamin, dan nomor telepon sebelum menyimpan pemesanan.

## # Cara Menjalankan Project
## Prasyarat
1. Java Development Kit (JDK) 8 atau lebih tinggi: Pastikan JDK sudah terpasang dan dikonfigurasi di sistem Anda.
2. IDE atau Teks Editor: Anda dapat menggunakan IDE seperti IntelliJ IDEA, Eclipse, atau teks editor dengan akses terminal.

## Langkah-Langkah cloning program
1. Buka terminal atau command prompt di direktori tempat Anda ingin menyimpan proyek.
2. Gunakan perintah `git clone` dengan git bash untuk mengunduh kode sumber dari repository
 -git clone https://github.com/Ihwannul86/UAP_PROGLAN_Booking_Villa.git
3. Setelah cloning selesai, navigasikan ke direktori proyek menggunakan perintah
 -cd UAP_PROGLAN_Booking_Villa
4. program siap di jalankan di VScode,Intellij IDEA, dll.
