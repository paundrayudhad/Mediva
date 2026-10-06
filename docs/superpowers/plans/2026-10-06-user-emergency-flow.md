# User & Emergency Reporting Flow Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Membangun seluruh 8 layar antarmuka pengguna (Role Penolong) aplikasi Mediva menggunakan Jetpack Compose dalam Light Mode Only, dengan warna tombol `#4A7FBF` (`MedivaPrimary`), foto ilustrasi `res/drawable/ilustrasi2.jpg`, komponen Geser untuk Kirim (*Slide-to-Confirm*), peta visualizer, dan navigasi terintegrasi dari Beranda hingga Menunggu Pengemudi.

**Architecture:** Modul terpisah di `com.aduhbingung.mediva.ui.user` yang mencakup state model laporan, navigasi bottom nav (`UserBottomNavBar`), komponen draggable slide-to-confirm, dan 8 layar composable interaktif dengan `@Preview`.

**Tech Stack:** Kotlin 2.2, Android Jetpack Compose (BOM 2026.02.01), Material3, JUnit4.

**Spec:** [docs/superpowers/specs/2026-10-06-user-emergency-flow-design.md](file:///d:/Mediva/docs/superpowers/specs/2026-10-06-user-emergency-flow-design.md)

## Global Constraints
- Light mode only (dengan preview dark pada layar lampiran foto opsional).
- Warna tombol utama fitur pengguna: `#4A7FBF` (`MedivaPrimary`).
- Gambar ilustrasi Beranda: `R.drawable.ilustrasi2`.
- Navigasi bawah 3 tab: Beranda, Lapor, Akun.
- Mekanisme geser untuk kirim (harus digeser hingga ujung kanan).

---

### Task 1: Model Navigasi, Data State & Komponen Bersama (*User Shared Components*)

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/model/UserRoute.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/model/EmergencyReportState.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/components/UserComponents.kt`
- Create: `app/src/test/java/com/aduhbingung/mediva/EmergencyReportTest.kt`

- [ ] **Step 1: Write failing unit test for report data validation**
- [ ] **Step 2: Run test to verify failure**
- [ ] **Step 3: Implement UserRoute, EmergencyReportState, and UserComponents (UserBottomNavBar, SlideToConfirmButton, MapCanvasCard)**
- [ ] **Step 4: Run test to verify pass**

---

### Task 2: Implementasi Layar 1 — Beranda Penolong (`UserHomeScreen`)

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/UserHomeScreen.kt`

- [ ] **Step 1: Implement UserHomeScreen**
  - Header: "Hallo, Narendra", subtitle, notifikasi, avatar
  - Header Card: `R.drawable.ilustrasi2` + tag lokasi "📍 Surakarta, Indonesia"
  - Emergency Banner `#4A7FBF`: "Butuh Bantuan Darurat?", subtitle, tombol putih "Laporkan Sekarang"
  - Seksi Layanan: 3 grid item (Lapor Darurat, Cari Ambulans, Riwayat Laporan)
  - Seksi Status: Ambulans Terdekat
  - `UserBottomNavBar` (Tab Beranda aktif)
  - `@Preview` composable
- [ ] **Step 2: Compile check**

---

### Task 3: Implementasi Layar 2, 3 & 4 — Lapor Darurat, Izin Kamera & Lampiran Foto

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/EmergencyReportScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/CameraPermissionScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/OptionalAttachmentScreen.kt`

- [ ] **Step 1: Implement EmergencyReportScreen** (4 jenis kejadian, box upload foto opsional, alamat, detail kejadian textarea, counter korban, tombol "Tinjau Laporan", bottom nav)
- [ ] **Step 2: Implement CameraPermissionScreen** (Badge kamera, judul edukasi, callout ambil dari tempat aman, tombol "Izinkan kamera", "Pilih dari galeri", "Lanjutkan tanpa foto")
- [ ] **Step 3: Implement OptionalAttachmentScreen** (Dark preview, status badge foto opsional, tombol "Gunakan foto ini" dan "Ganti foto dari galeri")
- [ ] **Step 4: Compile check**

---

### Task 4: Implementasi Layar 5 & 6 — Konfirmasi Lokasi & Tinjau Laporan (Slide to Send)

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/LocationConfirmationScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/ReportReviewScreen.kt`

- [ ] **Step 1: Implement LocationConfirmationScreen** (Peta canvas interaktif Surakarta, pin lokasi, badge "Lokasi aktif", rincian alamat akurasi 6 meter, callout percepatan bantuan, tombol "Lanjutkan ke tinjau laporan" dan "Ubah lokasi")
- [ ] **Step 2: Implement ReportReviewScreen** (Thumbnail foto, ringkasan lengkap data kejadian, info unit terdekat, **komponen Geser untuk kirim laporan**, tombol "Edit laporan")
- [ ] **Step 3: Compile check**

---

### Task 5: Implementasi Layar 7 & 8 — Laporan Berhasil Dikirim & Menunggu Pengemudi

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/ReportSentSuccessScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/screens/WaitingDriverScreen.kt`

- [ ] **Step 1: Implement ReportSentSuccessScreen** (Badge sukses, status "Terkirim", ringkasan data kejadian, 3 petunjuk tindakan selanjutnya, tombol "Pantau status laporan" & "Lihat detail laporan")
- [ ] **Step 2: Implement WaitingDriverScreen** (Preview peta, status tracker 3 tahap [Laporan terkirim -> Validasi driver -> Unit menuju lokasi], callout driver meninjau kejadian, tombol "Tetap di halaman ini" & "Kembali ke Beranda")
- [ ] **Step 3: Compile check**

---

### Task 6: Integrasi Navigasi Pengguna & Penghubungan ke `MedivaAuthApp`

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/user/UserNavHost.kt`
- Modify: `app/src/main/java/com/aduhbingung/mediva/ui/auth/MedivaAuthApp.kt`

- [ ] **Step 1: Implement UserNavHost** menghubungkan 8 layar pengguna secara mulus dengan back stack dan bottom nav switching.
- [ ] **Step 2: Hubungkan `AuthRoute.Home`** pada `MedivaAuthApp.kt` agar langsung memanggil `UserNavHost`.
- [ ] **Step 3: Jalankan verifikasi pengujian unit & build APK** (`testDebugUnitTest` dan `assembleDebug`).
