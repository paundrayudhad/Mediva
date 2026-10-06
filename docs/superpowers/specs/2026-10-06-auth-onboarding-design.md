# Spesifikasi Desain: Mediva Auth & Onboarding Flow (Android Jetpack Compose)

## 1. Ringkasan & Tujuan
Mengimplementasikan modul autentikasi dan onboarding untuk aplikasi Mediva berbasis **Android Jetpack Compose**, dengan kepatuhan penuh terhadap:
1. Referensi UI mockup desain (Light mode only, ilustrasi `res/drawable/ilustrasi1.png`, rounded top sheet layout).
2. Panduan alur resmi pada **`Panduan alur dan akses tracking laporan aktif.pdf`**:
   - `Sign In` → `B1 Izin Lokasi` → `Home`
   - `Sign Up` → `A2 Verifikasi Akun` (mode pendaftaran) → `B1 Izin Lokasi` → `Home`
   - `Sign In` (Lupa kata sandi) → `A1 Lupa Kata Sandi` → `A2 Verifikasi Akun` (mode pemulihan) → `A3 Atur Kata Sandi Baru` → `A4 Kata Sandi Berhasil Diubah` → `Sign In`
3. Pengaturan warna yang modular dan terisolasi:
   - Warna khusus tombol Auth: `#3F65E4` (`MedivaAuthPrimary`)
   - Warna background header Auth: `#CBDBFD` (`MedivaAuthBg`)
   - Warna tombol utama fitur aplikasi lain di luar Auth: `#4A7FBF` (`MedivaPrimary`)
   - Mode tampilan: **Light Mode Only**

---

## 2. Struktur Token & Tema (`ui/theme`)

### 2.1 Palet Warna (`Color.kt`)
- `MedivaPrimary`: `#4A7FBF` (Primary default untuk tombol/fitur di luar auth)
- `MedivaAuthPrimary`: `#3F65E4` (Khusus tombol utama & CTA di bagian auth)
- `MedivaAuthBg`: `#CBDBFD` (Biru lembut background header ilustrasi)
- `MedivaBackground`: `#F8FAFC`
- `MedivaSurface`: `#FFFFFF`
- `MedivaSurfaceVariant`: `#F1F5F9`
- `MedivaBorder`: `#E2E8F0`
- `MedivaTextPrimary`: `#0F172A`
- `MedivaTextSecondary`: `#64748B`
- `MedivaSuccess`: `#22C55E`
- `MedivaInfoContainer`: `#EBF2FF`

### 2.2 Aturan Tema (`Theme.kt`)
- Paksa **Light Color Scheme** (`darkTheme = false` secara eksplisit dan tidak override dengan dark mode sistem).
- Status bar dan navigation bar dikonfigurasi terang dengan icon kontras gelap.

---

## 3. Komponen Bersama (*Shared Components*) (`ui/auth/components`)

1. **`MedivaHeaderIllustration`**:
   - Container background `MedivaAuthBg` (`#CBDBFD`).
   - Tombol kembali (jika rute memiliki `onBackClick`).
   - Gambar ilustrasi `R.drawable.ilustrasi1` di tengah secara proporsional.
2. **`MedivaWhiteSheetContainer`**:
   - Kontainer putih di bagian bawah dengan radius sudut atas `RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)` dan bayangan lembut.
3. **`MedivaAuthTextField`**:
   - Label di atas field dengan gaya semi-bold.
   - Background input soft `#F8FAFC` / `#FFFFFF` dengan border tipis `#E2E8F0`.
   - Mendukung opsi password toggle (ikon mata) dan keyboard options (Email, Phone, Password).
4. **`MedivaAuthButton`**:
   - Background `MedivaAuthPrimary` (`#3F65E4`), text putih, font weight semi-bold/bold, rounded corner 12.dp.
   - Status disabled jika form belum valid atau kode OTP belum terisi.
5. **`SocialSignInButtons`**:
   - Tombol "Sign in with Google" dan "Sign in with Apple" dengan styling white outlined dan logo vektor.
6. **`OtpInputField`**:
   - 6 kotak digit terpisah dengan active border highlight, auto-jump kursor ke kotak berikutnya saat diketik.
7. **`SecurityInfoCard`**:
   - Box info berlatar `MedivaInfoContainer` dengan ikon dan teks penjelas keamanan / notifikasi kode.

---

## 4. Spesifikasi Layar & Navigasi

### 4.1 Rute Navigasi (`AuthRoute.kt`)
Menggunakan `sealed interface AuthRoute`:
- `SignIn`: Layar masuk utama
- `SignUp`: Pendaftaran langkah 1 (Data Diri)
- `VerifyOtp(val source: OtpSource, val destination: String)`: Layar A2 Verifikasi Akun.
  - Jika `source == OtpSource.SIGN_UP` -> validasi sukses mengarah ke `LocationPermission`.
  - Jika `source == OtpSource.PASSWORD_RESET` -> validasi sukses mengarah ke `ResetPassword`.
- `LocationPermission`: Layar B1 Izin Lokasi. Tombol "Izinkan Lokasi" atau "Pilih lokasi secara manual" mengarah ke `Home`.
- `ForgotPassword`: Layar A1 Lupa Kata Sandi. Input email/telepon -> kirim kode ke `VerifyOtp(source = OtpSource.PASSWORD_RESET)`.
- `ResetPassword`: Layar A3 Atur Kata Sandi Baru. Validasi 3 kriteria -> simpan mengarah ke `PasswordSuccess`.
- `PasswordSuccess`: Layar A4 Kata Sandi Berhasil Diubah. Tombol "Kembali ke masuk" -> kembali ke `SignIn`.
- `Home`: Dummy/placeholder halaman utama penolong (akan menampilkan ringkasan terhubung dari PRD / wireframe).

### 4.2 Detail Layar Sesuai Desain & Panduan
1. **`LoginScreen` (Sign In)**:
   - Header ilustrasi ambulans dengan `#CBDBFD`.
   - Judul: "Selamat Datang Di Mediva!"
   - Subtitle: "Login atau Register sekarang untuk menikmati layanan di Mediva"
   - Field: "Alamat Email / Nomor Telepon" & "Kata Sandi" (+ toggle mata).
   - Link: "Lupa kata sandi?" -> navigasi ke A1.
   - Tombol: "MASUK" (`#3F65E4`) -> navigasi ke B1 Izin Lokasi.
   - Social: "Sign in with Google", "Sign in with Apple".
   - Footer: "Belum punya akun Mediva? **Daftar Sekarang**" -> navigasi ke Sign Up.

2. **`RegisterScreen` (Sign Up - Langkah 1: Data Diri)**:
   - Icon medis `+` melingkar di atas.
   - Judul: "Daftar Akun Mediva!"
   - Stepper: "Langkah 1: Data Diri - Lengkapi informasi dasar untuk membuat akun baru".
   - Field: Nama lengkap, No Telp, Email, Password.
   - Checkbox: "Saya menyetujui S&K".
   - Tombol: "Daftar" (`#3F65E4`) -> navigasi ke A2 Verifikasi Akun (`source = SIGN_UP`).
   - Social & Footer: "Sudah punya akun? Masuk".

3. **`VerifyOtpScreen` (A2 Verifikasi Akun)**:
   - Top Bar dengan tombol kembali ke rute pemanggil (Sign Up atau A1).
   - Badge amplop surat biru muda.
   - Judul: "Periksa kode verifikasi".
   - Subtitle: "Masukkan 6 digit kode yang dikirim ke **mediva12@example.com**".
   - 6 kotak input kode OTP.
   - Teks: "Belum menerima kode? **Kirim ulang**".
   - Box keamanan berikon gembok.
   - Tombol: "Verifikasi Kode" (`#3F65E4`, hanya aktif bila 6 digit terisi).
   - Tombol kembali sekunder.

4. **`LocationPermissionScreen` (B1 Izin Lokasi)**:
   - Top Bar dengan tombol kembali.
   - Badge pin lokasi biru.
   - Judul: "Bantu petugas menemukan lokasi Anda".
   - Subtitle: "Izinkan akses lokasi saat menggunakan Mediva agar bantuan menuju ke titik kejadian yang tepat.".
   - 3 List item benefit:
     1. Lokasi laporan darurat (otomatisasi titik kejadian).
     2. Pelacakan bantuan (posisi ambulans dan penolong di peta).
     3. Tidak melacak terus-menerus (privasi & opsi manual).
   - Tombol Utama: "Izinkan Lokasi" (`#3F65E4`) -> navigasi ke Home.
   - Tombol Sekunder: "Pilih lokasi secara manual" -> navigasi ke Home.

5. **`ForgotPasswordScreen` (A1 Lupa Kata Sandi)**:
   - Header ilustrasi ambulans `#CBDBFD` + tombol kembali ke Sign In.
   - Judul: "Lupa kata sandi?".
   - Subtitle: "Masukkan email atau nomor telepon yang terdaftar untuk memulihkan akun Anda.".
   - Field: "Alamat Email / Nomor Telepon".
   - Info Box biru muda berikon surat.
   - Tombol: "Kirim kode verifikasi" (`#3F65E4`) -> navigasi ke A2 Verifikasi Akun (`source = PASSWORD_RESET`).
   - Tombol outline: "Kembali ke masuk" -> navigasi ke Sign In.

6. **`ResetPasswordScreen` (A3 Atur Kata Sandi Baru)**:
   - Header ilustrasi ambulans `#CBDBFD` + tombol kembali ke A2.
   - Judul: "Atur kata sandi baru".
   - Subtitle: "Gunakan kata sandi yang kuat dan berbeda dari kata sandi sebelumnya.".
   - Field: "Kata sandi baru" & "Konfirmasi kata sandi baru".
   - Indikator kriteria checklist real-time (centang hijau):
     - Minimal 8 karakter
     - Gabungkan huruf besar, huruf kecil, dan angka
     - Kedua kata sandi harus sama
   - Tombol: "Simpan kata sandi baru" (`#3F65E4`, aktif jika valid) -> navigasi ke A4.

7. **`PasswordSuccessScreen` (A4 Kata Sandi Berhasil Diubah)**:
   - Badge centang sukses biru bulat besar di tengah.
   - Judul: "Kata sandi berhasil diubah".
   - Subtitle: "Akun Anda siap digunakan kembali. Masuk dengan kata sandi baru Anda.".
   - Box keamanan berikon gembok.
   - Tombol: "Kembali ke masuk" (`#3F65E4`) -> navigasi ke Sign In.

---

## 5. Verifikasi & Pengujian
- Unit test logika validasi form (email/phone, password criteria match, OTP digit checking).
- Compile check dengan `./gradlew.bat assembleDebug`.
- Tampilan `@Preview` untuk setiap layar di Jetpack Compose tooling.
