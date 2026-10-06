# PRD — Smart Emergency Ambulance App (Hackathon MVP)

## 1. Ringkasan Produk

Aplikasi mobile untuk menghubungkan **penolong/pasien**, **petugas ambulans**, dan **petugas rumah sakit** dalam satu alur emergency response. Pengguna cukup menyampaikan lokasi dan kondisi korban secara sederhana. Sistem kemudian membantu memilih ambulans yang sesuai, mengarahkan petugas ke korban, melakukan penilaian ulang kondisi oleh petugas, lalu membantu memilih rumah sakit yang fasilitasnya sesuai dengan kebutuhan korban.

Fokus MVP hackathon adalah **alur end-to-end yang bisa didemokan**, bukan integrasi penuh dengan layanan kesehatan nyata.

### Nilai utama produk

1. User tidak perlu memilih ambulans secara manual.
2. User tidak perlu memilih rumah sakit secara manual.
3. Sistem mempertimbangkan kondisi korban dan fasilitas yang tersedia.
4. Petugas ambulans dapat memperbarui kondisi korban di lokasi.
5. Rumah sakit menerima informasi pasien sebelum pasien tiba.
6. Semua interaksi dilakukan melalui aplikasi mobile.

---

## 2. Tujuan MVP Hackathon

### Tujuan utama
Mendemonstrasikan satu kasus emergency dari awal hingga selesai:

**Request bantuan → matching ambulans → ambulans datang → assessment korban → matching rumah sakit → rumah sakit menerima → perjalanan → handover → selesai.**

### Bukan target MVP

- Pembayaran.
- BPJS.
- Rekam medis lengkap.
- Integrasi SATUSEHAT.
- Integrasi sistem rumah sakit sungguhan.
- Integrasi telepon darurat pemerintah.
- AI diagnosis.
- Penentuan tindakan medis.
- Billing rumah sakit.
- Multi-organisasi kompleks.
- Dispatch center berbasis web.

---

## 3. Role Pengguna

### 3.1 User / Penolong
Orang yang berada bersama korban dan meminta ambulans.

Tugas utama:
- Menentukan lokasi kejadian.
- Menjelaskan kondisi korban dengan bahasa sederhana.
- Mengirim emergency request.
- Melihat status ambulans.
- Melihat live tracking.
- Menghubungi petugas ambulans.
- Melihat rumah sakit tujuan setelah ditentukan.

### 3.2 Petugas Ambulans
Petugas yang menerima emergency request dan membawa korban.

Tugas utama:
- Mengatur status tersedia/tidak tersedia.
- Menerima atau menolak request.
- Melihat lokasi korban.
- Mengubah status perjalanan.
- Mengisi assessment korban setelah tiba.
- Melihat rumah sakit yang direkomendasikan.
- Memulai perjalanan ke rumah sakit.
- Melakukan handover.

### 3.3 Petugas Rumah Sakit
Petugas penerima incoming patient.

Tugas utama:
- Melihat incoming patient.
- Melihat ETA.
- Melihat ringkasan kondisi korban.
- Melihat kebutuhan fasilitas.
- Menyatakan siap atau tidak dapat menerima.
- Mengonfirmasi pasien telah diterima.

---

## 4. Prinsip UX

### 4.1 Sedikit keputusan saat emergency
User tidak diminta memahami tipe ambulans, spesialisasi rumah sakit, atau istilah medis teknis.

### 4.2 Input satu per satu
Pertanyaan kondisi ditampilkan step-by-step agar tidak terasa seperti formulir panjang.

### 4.3 Selalu ada opsi “Tidak tahu”
User tidak boleh dipaksa memberikan informasi medis yang tidak ia pahami.

### 4.4 Status harus human-readable
Contoh:
- “Mencari ambulans” bukan `SEARCHING`.
- “Ambulans menuju lokasi” bukan `AMBULANCE_OTW`.

### 4.5 Re-assessment oleh tenaga lapangan
Input user hanya dianggap informasi awal. Setelah petugas tiba, kondisi korban diperbarui oleh petugas ambulans.

### 4.6 Sistem memberi rekomendasi, bukan diagnosis
Aplikasi dapat menyampaikan:
- “Membutuhkan ambulans dengan dukungan oksigen.”
- “Mencari rumah sakit dengan layanan trauma.”

Aplikasi tidak menyampaikan diagnosis medis definitif.

---

# 5. FLOW UTAMA — USER / PENOLONG

## U1. Launch / Welcome

### Tujuan
Memberikan akses tercepat ke emergency request.

### Komponen
- Logo aplikasi.
- CTA utama: **Panggil Ambulans**.
- Secondary action: Riwayat bantuan.
- Informasi lokasi saat ini.

### UX rule
CTA emergency selalu berada di area paling mudah dijangkau.

---

## U2. Permission Lokasi

Jika lokasi belum diizinkan:

### Copy
“Lokasi dibutuhkan agar ambulans dapat menemukan Anda.”

### Action
- Izinkan lokasi.
- Masukkan lokasi manual.

### Input manual
- Nama jalan / tempat.
- Detail patokan.
- Pin di peta.

---

## U3. Konfirmasi Lokasi Kejadian

### Data otomatis
- Latitude.
- Longitude.
- Alamat hasil reverse geocoding.

### Input user
- Patokan lokasi, opsional.
- Catatan akses, opsional.

Contoh:
- “Depan minimarket.”
- “Masuk gang sebelah masjid.”

### CTA
**Lokasi Sudah Benar**

---

## U4. Pilih Jenis Kejadian

### Pertanyaan
**Apa yang terjadi?**

### Pilihan MVP
- Kecelakaan.
- Tidak sadarkan diri.
- Sulit bernapas.
- Nyeri dada.
- Pendarahan.
- Persalinan.
- Luka bakar.
- Lainnya.

### Input tambahan jika “Lainnya”
Deskripsi singkat, maksimal 200 karakter.

---

## U5. Quick Condition Check

Pertanyaan dapat menyesuaikan tipe kejadian, tetapi untuk MVP cukup beberapa pertanyaan umum.

### Pertanyaan 1 — Kesadaran
“Apakah korban sadar?”
- Ya.
- Tidak.
- Tidak tahu.

### Pertanyaan 2 — Pernapasan
“Apakah korban dapat bernapas?”
- Normal.
- Kesulitan.
- Tidak bernapas.
- Tidak tahu.

### Pertanyaan 3 — Pendarahan
“Apakah ada pendarahan?”
- Tidak ada.
- Ringan.
- Berat.
- Tidak tahu.

### Pertanyaan 4 — Jumlah korban
- Stepper 1–5+.

### Pertanyaan 5 — Kelompok usia
- Bayi / balita.
- Anak.
- Remaja/dewasa.
- Lansia.
- Tidak tahu.

### Optional
Catatan singkat kondisi.

---

## U6. Kontak Penolong

### Input
- Nama penolong.
- Nomor telepon aktif.

Jika user sudah login, field dapat terisi otomatis.

### UX
Nomor telepon dibutuhkan agar petugas dapat menghubungi penolong ketika kesulitan menemukan lokasi.

---

## U7. Review Emergency Request

### Tampilkan
- Lokasi.
- Jenis kejadian.
- Ringkasan kondisi.
- Jumlah korban.
- Nomor kontak.

### CTA utama
**Cari Bantuan Sekarang**

### Secondary
Edit lokasi / edit kondisi.

---

## U8. Searching Ambulance

### Tampilan status progres
1. Lokasi diterima.
2. Kondisi korban diterima.
3. Mencari ambulans yang sesuai.
4. Menunggu petugas menerima.

### UX penting
User tetap dapat melihat:
- Lokasi kejadian.
- Tombol batal.
- Tombol hubungi emergency contact jika tersedia di implementasi.

### Timeout state
Jika kandidat pertama menolak:
“Sedang mencari ambulans lain.”

Jika belum ada unit tersedia:
“Belum ada unit yang dapat menerima. Sistem terus mencari.”

Untuk demo hackathon, sistem dapat langsung menemukan unit simulasi.

---

## U9. Ambulance Assigned

### Informasi
- Nama / kode ambulans.
- Nama petugas.
- ETA.
- Jarak.
- Fasilitas relevan.
- Status.

### CTA
- Hubungi petugas.
- Bagikan perjalanan.

### Copy
“Ambulans dipilih sesuai kondisi awal korban dan ketersediaan unit.”

---

## U10. Live Tracking

### Map
- Posisi korban.
- Posisi ambulans.
- Rute.

### Status dinamis
- Ambulans menuju lokasi.
- Ambulans hampir tiba.
- Ambulans telah tiba.

### Informasi tambahan
- ETA.
- Petugas.
- Nomor unit.

---

## U11. Ambulance Arrived

### Tampilkan
“Ambulans telah tiba.”

### Instruction
- Pastikan akses menuju korban terbuka.
- Beri petunjuk lokasi jika diperlukan.

### Setelah petugas mengambil alih
User tidak perlu mengisi assessment berikutnya.

---

## U12. Hospital Selected

Setelah assessment petugas ambulans, user melihat:
- Nama rumah sakit tujuan.
- ETA perjalanan.
- Status rumah sakit.
- Fasilitas yang relevan.

Contoh:
“RS ABC siap menerima pasien.”

---

## U13. Patient Transport

### Tampilkan
- Ambulans sedang menuju rumah sakit.
- ETA.
- Tujuan.
- Live tracking jika diperlukan.

---

## U14. Completed

### Kondisi selesai
Rumah sakit mengonfirmasi pasien telah diterima.

### Tampilkan
“Pasien telah diterima oleh RS ABC.”

### Optional
- Simpan riwayat.
- Lihat ringkasan kejadian.

---

# 6. FLOW PETUGAS AMBULANS

## A1. Login / Role Access

### Input
- Nomor telepon / email.
- Password / PIN.

Untuk hackathon boleh menggunakan akun dummy.

---

## A2. Availability

### Status
- Available.
- Busy.
- Offline.

### Home Ambulance
Tampilkan:
- Status unit.
- Unit ID.
- Fasilitas utama unit.
- Lokasi unit.

CTA:
**Mulai Menerima Request**

---

## A3. Incoming Emergency Request

### Tampilkan
- Jarak ke korban.
- ETA.
- Jenis kejadian.
- Ringkasan kondisi awal.
- Jumlah korban.
- Lokasi.
- Catatan akses.

### CTA
- Terima.
- Tolak.

### Jika menolak
Alasan sederhana:
- Sedang tidak siap.
- Unit bermasalah.
- Kondisi di luar kemampuan unit.
- Lainnya.

---

## A4. Navigate to Patient

### Tampilkan
- Map.
- Rute.
- ETA.
- Nama penolong.
- Tombol telepon.

### Action
- Mulai perjalanan.
- Tiba di lokasi.

---

## A5. Arrival Confirmation

CTA:
**Saya Sudah Tiba**

Setelah ditekan:
- Status user berubah menjadi “Ambulans telah tiba.”

---

## A6. Start Patient Assessment

CTA:
**Mulai Penanganan**

### Tujuan
Mengubah informasi awal user menjadi kondisi lapangan yang lebih terstruktur.

---

## A7. Assessment — Kesadaran

### Input
- Sadar penuh.
- Respons menurun.
- Tidak sadar.

Optional:
Catatan.

---

## A8. Assessment — Pernapasan

### Input
- Normal.
- Kesulitan.
- Tidak bernapas.

Optional:
- Membutuhkan dukungan oksigen: Ya/Tidak.

---

## A9. Assessment — Sirkulasi / Pendarahan

### Input
- Tidak ada pendarahan bermakna.
- Pendarahan ringan.
- Pendarahan berat.

Optional:
- Tanda syok dicurigai: Ya/Tidak/Tidak tahu.

---

## A10. Assessment — Area Cedera

### Multi-select
- Kepala.
- Leher.
- Dada.
- Perut.
- Punggung.
- Lengan.
- Kaki.
- Luka bakar.
- Tidak terlihat cedera.
- Lainnya.

---

## A11. Assessment — Special Condition

### Input opsional
- Kehamilan / persalinan.
- Anak/bayi.
- Kejang.
- Riwayat jantung diketahui.
- Lainnya.

### Catatan
Input ini hanya untuk membantu kebutuhan fasilitas tujuan, bukan membuat diagnosis.

---

## A12. Assessment Summary

### Tampilkan
Ringkasan seluruh kondisi.

### CTA
**Cari Rumah Sakit yang Sesuai**

---

## A13. Hospital Matching

### Tampilkan progres
- Kondisi korban diperbarui.
- Mencari fasilitas yang sesuai.
- Mengecek rumah sakit tujuan.
- Menunggu konfirmasi.

---

## A14. Suggested Hospital

### Informasi
- Nama rumah sakit.
- Jarak.
- ETA.
- Kesesuaian fasilitas.
- Status konfirmasi.

### Fasilitas contoh
- IGD.
- ICU.
- CT Scan.
- Bedah/Trauma.
- Kardiologi.
- Obgyn.
- NICU.

### UX
Petugas melihat alasan singkat kenapa RS dipilih.

Contoh:
“Dipilih karena memiliki IGD, CT Scan, ICU, dan layanan trauma yang sesuai dengan kondisi korban.”

---

## A15. Hospital Accepted

Setelah rumah sakit menerima:
“RS ABC siap menerima pasien.”

CTA:
**Mulai Perjalanan ke Rumah Sakit**

---

## A16. Hospital Rejected

Jika rumah sakit menolak:
“RS ABC belum dapat menerima pasien.”

Sistem otomatis masuk kembali ke:
**Mencari rumah sakit alternatif.**

Petugas tidak perlu mengulang assessment.

---

## A17. Transport to Hospital

### Tampilkan
- Map.
- Tujuan.
- ETA.
- Status RS siap menerima.

### Action
**Tiba di Rumah Sakit**

---

## A18. Handover

### Tampilkan
- Ringkasan kondisi pasien.
- Rumah sakit.
- Waktu tiba.

CTA:
**Serahkan Pasien**

Status kemudian menunggu konfirmasi rumah sakit.

---

## A19. Completed

Setelah hospital mengonfirmasi:
“Pasien telah diterima.”

Unit kembali menjadi:
**Available**

---

# 7. FLOW PETUGAS RUMAH SAKIT

## H1. Login

Akun rumah sakit dummy untuk demo.

---

## H2. Hospital Status

### Home
- Nama RS.
- Status menerima pasien: Open / Limited / Closed.
- Incoming patients.

Untuk MVP, status RS dapat diubah manual.

---

## H3. Incoming Patient Notification

### Push/in-app notification
“Pasien emergency menuju rumah sakit Anda.”

### Tampilkan
- ETA.
- Jenis kejadian.
- Ringkasan kondisi.
- Fasilitas yang dibutuhkan.
- Unit ambulans.

---

## H4. Incoming Patient Detail

### Kondisi
- Kesadaran.
- Pernapasan.
- Pendarahan.
- Area cedera.
- Kondisi khusus.

### Kebutuhan
- IGD.
- ICU.
- CT Scan.
- Trauma.
- Layanan lain.

### CTA
- Siap menerima.
- Tidak dapat menerima.

---

## H5. Reject Reason

Jika tidak menerima:
- Fasilitas tidak tersedia.
- Kapasitas penuh.
- Layanan tidak tersedia.
- Lainnya.

Sistem kemudian mencari RS alternatif.

---

## H6. Accepted Patient

Status:
“Pasien dalam perjalanan.”

### Tampilkan
- ETA live.
- Ambulans.
- Ringkasan korban.

---

## H7. Ambulance Arrived

Notifikasi:
“Ambulans telah tiba.”

CTA:
**Konfirmasi Pasien Diterima**

---

## H8. Completed

Kasus dipindahkan ke history.

---

# 8. ALUR STATUS END-TO-END

## Emergency Request

1. DRAFT
2. SUBMITTED
3. SEARCHING_AMBULANCE
4. AMBULANCE_ASSIGNED
5. AMBULANCE_EN_ROUTE
6. AMBULANCE_ARRIVED
7. PATIENT_ASSESSMENT
8. SEARCHING_HOSPITAL
9. HOSPITAL_PENDING
10. HOSPITAL_CONFIRMED
11. PATIENT_TRANSPORT
12. ARRIVED_HOSPITAL
13. HANDOVER_PENDING
14. COMPLETED

### Exception status
- CANCELLED
- NO_AMBULANCE_AVAILABLE
- HOSPITAL_REJECTED (event, bukan final status)

---

# 9. MATCHING EXPERIENCE — PERSPEKTIF UX

## Ambulans
Sistem mempertimbangkan tiga kelompok informasi:

1. **Kondisi awal korban**
2. **Kemampuan/fasilitas ambulans**
3. **Jarak dan availability**

User hanya melihat hasil akhir:
“Ambulans yang sesuai sedang menuju lokasi.”

## Rumah Sakit
Sistem mempertimbangkan:

1. Assessment petugas ambulans.
2. Fasilitas rumah sakit.
3. Status menerima pasien.
4. Jarak/ETA.

Petugas ambulans melihat:
“RS ABC dipilih karena memiliki fasilitas yang sesuai.”

---

# 10. KONDISI → KEBUTUHAN FASILITAS UNTUK DEMO

Ini bukan guideline klinis, hanya skenario demo untuk menunjukkan matching UX.

## Kecelakaan + tidak sadar
Ambulans:
- Oxygen.
- AED.
- Trauma kit.

Rumah sakit:
- IGD.
- CT Scan.
- ICU.
- Trauma/bedah.

## Sulit bernapas
Ambulans:
- Oxygen.
- Airway kit.

Rumah sakit:
- IGD.
- ICU.
- Respiratory support.

## Nyeri dada
Ambulans:
- AED.
- ECG.
- Oxygen.

Rumah sakit:
- IGD.
- Cardiology.
- ICU.

## Persalinan
Ambulans:
- Delivery kit.
- Oxygen.

Rumah sakit:
- IGD.
- Obgyn.
- Maternity.
- NICU jika diperlukan.

---

# 11. NOTIFIKASI UTAMA

## User
- Request berhasil dikirim.
- Ambulans ditemukan.
- Ambulans menuju lokasi.
- Ambulans hampir tiba.
- Ambulans tiba.
- Rumah sakit tujuan ditemukan.
- RS siap menerima.
- Pasien telah diterima rumah sakit.

## Ambulance
- Emergency request baru.
- Request berhasil diterima.
- Rumah sakit ditemukan.
- Rumah sakit menerima pasien.
- Rumah sakit menolak pasien.
- Handover dikonfirmasi.

## Hospital
- Incoming patient baru.
- ETA berubah signifikan.
- Ambulans hampir tiba.
- Ambulans tiba.

---

# 12. ERROR / EDGE CASE UX

## Lokasi tidak ditemukan
“Lokasi belum dapat ditentukan. Geser pin atau masukkan alamat.”

## Ambulans pertama menolak
“Sedang mencari unit lain.”

## Tidak ada ambulans
“Terdapat keterbatasan unit. Sistem terus mencari bantuan.”

Untuk MVP demo, selalu sediakan satu ambulans dummy.

## Rumah sakit menolak
“Terdapat perubahan tujuan. Sistem sedang mencari rumah sakit alternatif.”

## Koneksi terputus
- Pertahankan state terakhir.
- Tampilkan banner “Koneksi terputus. Mencoba menghubungkan kembali.”

## User membatalkan
Konfirmasi:
“Batalkan permintaan ambulans?”

Jika ambulans sudah menuju lokasi, tampilkan warning lebih kuat.

---

# 13. PRIORITAS IMPLEMENTASI HACKATHON

## P0 — Harus ada
- Role user.
- Role ambulance.
- Role hospital.
- Request emergency.
- Lokasi korban.
- Quick condition check.
- Matching ambulans simulasi.
- Ambulance accept.
- Status perjalanan.
- Assessment petugas.
- Matching hospital simulasi.
- Hospital accept/reject.
- Re-route hospital setelah reject.
- Handover.
- Completed.

## P1 — Sangat bagus jika ada
- Live map.
- Realtime status update.
- ETA.
- Notification.
- Share tracking.

## P2 — Future
- Voice input.
- AI-assisted information extraction.
- Integration official emergency service.
- Hospital occupancy realtime.
- Medical device integration.
- Multi-patient incident.

---

# 14. ACCEPTANCE SCENARIO DEMO

## Scenario: Kecelakaan

1. User membuka app.
2. Tekan Panggil Ambulans.
3. Lokasi otomatis terisi.
4. Pilih Kecelakaan.
5. Korban: tidak sadar, kesulitan bernapas, pendarahan berat.
6. User submit.
7. Ambulans AMB-02 dipilih.
8. Petugas menerima.
9. User melihat ambulans bergerak.
10. Petugas tiba.
11. Petugas assessment: cedera kepala + kondisi awal.
12. Sistem memilih RS A.
13. RS A menolak karena kapasitas.
14. Sistem otomatis mencari RS B.
15. RS B menerima.
16. Ambulans membawa pasien.
17. RS B melihat ETA.
18. Ambulans tiba.
19. RS B mengonfirmasi pasien diterima.
20. Kasus selesai.

Scenario ini menunjukkan seluruh value produk tanpa membutuhkan banyak cabang fitur.

---

# 15. DEFINISI SUKSES HACKATHON

MVP dianggap berhasil jika tiga device dapat mendemonstrasikan:

- Device 1 sebagai User.
- Device 2 sebagai Ambulance.
- Device 3 sebagai Hospital.

Dan perubahan status satu role terlihat oleh role lain tanpa harus menjelaskan proses backend kepada juri.
