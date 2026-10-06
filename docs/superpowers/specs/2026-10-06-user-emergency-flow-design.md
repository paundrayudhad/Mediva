# Spesifikasi Desain: User & Emergency Reporting Flow (Android Jetpack Compose)

## 1. Ringkasan & Tujuan
Mengimplementasikan modul antarmuka pengguna (Role Penolong / Pasien) pada aplikasi Mediva dengan 8 layar lengkap sesuai mockup referensi, PRD (`ambulance_hackathon_prd.md`), dan panduan alur navigasi (`Panduan alur dan akses tracking laporan aktif.pdf`).

### Nilai Utama:
- Tampilan Beranda (*Home*) yang informatif dan terintegrasi dengan aset `res/drawable/ilustrasi2.jpg`.
- Akses emergency satu ketukan (*One-tap Emergency Call*) dan formulir lapor darurat berstruktur jelas.
- Dukungan alur lampiran foto opsional (Izin Kamera B2 dan preview foto).
- Konfirmasi lokasi kejadian berbasis peta dan validasi radius akurasi.
- Mekanisme **Slide to Send** (*Geser untuk kirim laporan*) untuk mencegah pengiriman tidak disengaja.
- Pelacakan status laporan *real-time* (Terkirim -> Menunggu validasi driver -> Menuju lokasi).

---

## 2. Token Warna & Styling
- **Warna Utama Tombol/CTA Fitur Pengguna**: `#4A7FBF` (`MedivaPrimary`)
- **Mode Tampilan**: Light Mode Only (dengan pengecualian layar preview foto opsional bergaya dark fokus)
- **Background Utama**: `#F8FAFC` & `#FFFFFF`
- **Ilustrasi Beranda**: `R.drawable.ilustrasi2`
- **Bottom Navigation**: 3 menu tetap: Beranda, Lapor, Akun

---

## 3. Struktur Rute Navigasi (`UserRoute.kt`)

```kotlin
sealed interface UserRoute {
    data object Home : UserRoute
    data object EmergencyReport : UserRoute
    data object CameraPermission : UserRoute
    data object OptionalAttachment : UserRoute
    data object LocationConfirmation : UserRoute
    data object ReportReview : UserRoute
    data object ReportSentSuccess : UserRoute
    data object WaitingDriver : UserRoute
}
```

---

## 4. Rincian 8 Layar Pengguna

### 4.1 Layar 1 — Beranda Penolong (`UserHomeScreen`)
- **Header**: Salam personalized *"Hallo, Narendra"*, subtitle *"Bagaimana keadaanmu hari ini?"*, ikon notifikasi lonceng & foto profil.
- **Card Ilustrasi**: Menampilkan `R.drawable.ilustrasi2` dengan tag lokasi *"📍 Surakarta, Indonesia"*.
- **Hero Banner Emergency**: Latar belakang biru gradien/solid `#4A7FBF`, judul *"Butuh Bantuan Darurat?"*, teks pendukung, tombol putih teks biru *"Laporkan Sekarang"*.
- **Menu Layanan (3 Grid Card)**:
  - *Lapor Darurat* (Ikon segitiga darurat)
  - *Cari Ambulans* (Ikon ambulans)
  - *Riwayat Laporan* (Ikon dokumen riwayat)
- **Status Ambulans Terdekat**: Kartu kesiapan unit di sekitar area Surakarta.
- **Bottom Navigation**: Tab Beranda aktif.

### 4.2 Layar 2 — Formulir Lapor Darurat (`EmergencyReportScreen`)
- **Header**: Top bar dengan tombol kembali & judul *"Lapor Darurat"*.
- **1. Jenis Kejadian**: Pilihan kartu/chip interaktif (*Kecelakaan*, *Kondisi Medis*, *Kebakaran*, *Lainnya*).
- **2. Foto Kejadian (Opsional)**: Box dashed border dengan ikon kamera dan teks petunjuk -> Membuka alur foto/kamera.
- **3. Lokasi Kejadian**: Card lokasi *"Jl. Slamet Riyadi, Surakarta"* dengan opsi *"Ubah Lokasi"* -> membuka Konfirmasi Lokasi.
- **4. Detail Kejadian**: Input teks area untuk keterangan kejadian.
- **5. Jumlah Korban**: Stepper counter interaktif (`-` `1` `+`).
- **CTA**: Tombol **"Tinjau Laporan"** (`#4A7FBF`) -> Navigasi ke `LocationConfirmation` / `ReportReview`.

### 4.3 Layar 3 — Izin Kamera B2 (`CameraPermissionScreen`)
- Sesuai spesifikasi B2 pada PDF panduan.
- Badge kamera biru muda melingkar di tengah.
- Judul: *"Foto membantu petugas memahami kejadian"*.
- Subtitle: *"Izinkan kamera untuk mengambil foto kondisi korban dan mendampingkannya ke laporan darurat."*
- Callout peringatan keselamatan: *"Ambil foto dari tempat aman — Mengambil foto tidak boleh sampai memperlambat pertolongan atau membahayakan diri Anda."*
- Card privasi foto.
- Tombol:
  - Utama: **"Izinkan kamera"** (`#4A7FBF`)
  - Sekunder: **"Pilih dari galeri"**
  - Teks Link: **"Lanjutkan tanpa foto"**

### 4.4 Layar 4 — Lampiran Foto Opsional (`OptionalAttachmentScreen`)
- Tampilan fokus gelap dengan latar belakang preview gambar malam / ambulans.
- Badge status: *"Foto opsional terpasang"*.
- Teks penjelas bantuan verifikasi laporan darurat.
- Tombol:
  - Utama: **"Gunakan foto ini"** -> Kembali ke formulir lapor dengan foto aktif.
  - Sekunder: **"Ganti foto dari galeri"**.

### 4.5 Layar 5 — Konfirmasi Lokasi (`LocationConfirmationScreen`)
- **Peta Interaktif / Map Visualizer**: Menampilkan visual peta Surakarta, pin lokasi di tengah, dan chip *"Lokasi aktif"*.
- **Sheet Konfirmasi Bawah**:
  - Judul: *"Pastikan titik kejadian sudah benar"*.
  - Detail lokasi: *"Jl. Slamet Riyadi, Surakarta"* + badge *"Akurat (6 meter) • Lokasi saat ini"*.
  - Callout info pentingnya keakuratan titik jemput.
  - Tombol: **"Lanjutkan ke tinjau laporan"** (`#4A7FBF`) & tombol outline *"Ubah lokasi"*.

### 4.6 Layar 6 — Tinjau Laporan & Geser Kirim (`ReportReviewScreen`)
- Banner thumbnail foto terlampir di atas.
- Card Ringkasan Kejadian terstruktur:
  - Jenis Kejadian (*Kecelakaan lalu lintas*)
  - Lokasi (*Jl. Slamet Riyadi, Surakarta*)
  - Jumlah Korban (*1 orang*)
  - Keterangan (*Tabrakan sepeda motor, satu korban tergeletak...*)
- Callout pencarian unit otomatis.
- **Komponen Slide to Send**:
  - Handle geser meluncur dari kiri ke kanan dengan ikon panah.
  - Teks: *"Geser untuk kirim laporan"*.
  - Catatan di bawah: *"Tarik handle hingga ujung kanan; ketuk saja tidak mengirimkan laporan."*
- Tombol teks: *"Edit laporan"*.

### 4.7 Layar 7 — Laporan Berhasil Dikirim (`ReportSentSuccessScreen`)
- Badge centang sukses biru bulat di tengah atas.
- Judul: *"Laporan berhasil dikirim"*.
- Subtitle: *"Laporan diteruskan ke pengemudi untuk diproses. Menunggu respons pengemudi."*
- Card ringkasan data terkirim dengan chip status hijau *"Terkirim"*.
- 3 Petunjuk Tindakan Selanjutnya:
  1. *Tetap di lokasi yang aman*
  2. *Pastikan nomor HP aktif*
  3. *Pantau status bantuan*
- Tombol: **"Pantau status laporan"** (`#4A7FBF`) -> Navigasi ke `WaitingDriver`.
- Tombol sekunder: *"Lihat detail laporan"*.

### 4.8 Layar 8 — Menunggu Pengemudi (`WaitingDriverScreen`)
- Header: *"Menunggu Pengemudi"*, subtitle *"Laporan terkirim, menunggu konfirmasi driver."*
- Map preview posisi laporan aktif.
- Card Status Tracker 3 Tahap:
  1. `Laporan terkirim` (Status: Selesai / Centang biru)
  2. `Validasi driver` (Status: Aktif / Menunggu driver meninjau & menerima)
  3. `Unit menuju lokasi` (Status: Mendatang)
- Info callout peninjauan driver sebelum menerima laporan.
- Tombol: **"Tetap di halaman ini"** & **"Kembali ke Beranda"**.

---

## 5. Komponen Bersama (*Shared Components*)
- `UserBottomNavBar`: Navigasi 3 tab (Beranda, Lapor, Akun).
- `SlideToConfirmButton`: Komponen draggable handle dengan animasi snapping dan trigger aksi saat mencapai 90%+ lebar track.
- `MapCanvasCard`: Visualizer peta vektor/simulasi peta interaktif dengan pin dan akurasi GPS.
