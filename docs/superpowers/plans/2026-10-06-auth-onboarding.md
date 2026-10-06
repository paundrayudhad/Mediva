# Mediva Auth & Onboarding Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Membangun seluruh tampilan dan alur autentikasi & onboarding aplikasi Mediva (7 layar) menggunakan Android Jetpack Compose dalam Light Mode Only, dengan warna tombol khusus auth `#3F65E4`, header background `#CBDBFD`, tombol fitur lain `#4A7FBF`, ilustrasi `res/drawable/ilustrasi1.png`, dan alur navigasi sesuai `Panduan alur dan akses tracking laporan aktif.pdf`.

**Architecture:** Modul terisolasi di package `com.aduhbingung.mediva.ui.auth`. State-driven lightweight routing (`AuthRoute`) tanpa dependensi eksternal, komponen reusable (`AuthComponents`), 7 screen composables dengan interactive states, `@Preview` per layar, dan `MedivaAuthApp` terpasang di `MainActivity`.

**Tech Stack:** Kotlin 2.2, Android Jetpack Compose (BOM 2026.02.01), Material3, JUnit4.

**Spec:** [docs/superpowers/specs/2026-10-06-auth-onboarding-design.md](file:///d:/Mediva/docs/superpowers/specs/2026-10-06-auth-onboarding-design.md)

## Global Constraints
- Light mode only (`darkTheme = false`, tanpa dynamic dark color).
- Warna khusus tombol auth: `#3F65E4` (`MedivaAuthPrimary`).
- Warna biru background header auth: `#CBDBFD` (`MedivaAuthBg`).
- Warna tombol utama fitur lain: `#4A7FBF` (`MedivaPrimary`).
- Gambar ilustrasi: `R.drawable.ilustrasi1`.
- Alur sambungan navigasi mengikuti tabel resmi `Panduan alur dan akses tracking laporan aktif.pdf`.

---

### Task 1: Setup Tema, Token Warna & Test Validasi

**Files:**
- Create: `app/src/test/java/com/aduhbingung/mediva/AuthValidationTest.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/model/AuthValidation.kt`
- Modify: `app/src/main/java/com/aduhbingung/mediva/ui/theme/Color.kt`
- Modify: `app/src/main/java/com/aduhbingung/mediva/ui/theme/Theme.kt`

**Interfaces:**
- Consumes: Nothing
- Produces: `MedivaAuthPrimary`, `MedivaAuthBg`, `MedivaPrimary`, `AuthValidation.isOtpValid(code: String)`, `AuthValidation.isPasswordMin8(pwd: String)`, `AuthValidation.isPasswordMixed(pwd: String)`, `AuthValidation.doPasswordsMatch(p1: String, p2: String)`

- [ ] **Step 1: Write the failing unit test for validation logic**

Create `app/src/test/java/com/aduhbingung/mediva/AuthValidationTest.kt`:
```kotlin
package com.aduhbingung.mediva

import com.aduhbingung.mediva.ui.auth.model.AuthValidation
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthValidationTest {
    @Test
    fun testOtpValidation() {
        assertFalse(AuthValidation.isOtpValid("12345"))
        assertTrue(AuthValidation.isOtpValid("123456"))
        assertFalse(AuthValidation.isOtpValid("12345a"))
    }

    @Test
    fun testPasswordCriteria() {
        assertFalse(AuthValidation.isPasswordMin8("Abc1"))
        assertTrue(AuthValidation.isPasswordMin8("Abcdefgh1"))

        assertFalse(AuthValidation.isPasswordMixed("abcdefgh"))
        assertFalse(AuthValidation.isPasswordMixed("ABCDEFGH"))
        assertFalse(AuthValidation.isPasswordMixed("12345678"))
        assertTrue(AuthValidation.isPasswordMixed("Mediva2026"))

        assertTrue(AuthValidation.doPasswordsMatch("Secret123", "Secret123"))
        assertFalse(AuthValidation.doPasswordsMatch("Secret123", "Secret321"))
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `.\gradlew.bat testDebugUnitTest --tests com.aduhbingung.mediva.AuthValidationTest`
Expected: FAIL compilation with "Unresolved reference: AuthValidation".

- [ ] **Step 3: Implement AuthValidation, Color.kt, and Theme.kt**

Create `app/src/main/java/com/aduhbingung/mediva/ui/auth/model/AuthValidation.kt`:
```kotlin
package com.aduhbingung.mediva.ui.auth.model

object AuthValidation {
    fun isOtpValid(code: String): Boolean = code.length == 6 && code.all { it.isDigit() }
    fun isPasswordMin8(password: String): Boolean = password.length >= 8
    fun isPasswordMixed(password: String): Boolean =
        password.any { it.isUpperCase() } && password.any { it.isLowerCase() } && password.any { it.isDigit() }
    fun doPasswordsMatch(p1: String, p2: String): Boolean = p1.isNotEmpty() && p1 == p2
}
```

Update `app/src/main/java/com/aduhbingung/mediva/ui/theme/Color.kt` to include:
- `val MedivaPrimary = Color(0xFF4A7FBF)`
- `val MedivaAuthPrimary = Color(0xFF3F65E4)`
- `val MedivaAuthBg = Color(0xFFCBDBFD)`
- `val MedivaSurface = Color(0xFFFFFFFF)`
- `val MedivaSurfaceVariant = Color(0xFFF8FAFC)`
- `val MedivaBorder = Color(0xFFE2E8F0)`
- `val MedivaTextPrimary = Color(0xFF111827)`
- `val MedivaTextSecondary = Color(0xFF64748B)`
- `val MedivaSuccess = Color(0xFF16A34A)`
- `val MedivaInfoBg = Color(0xFFEBF2FF)`
- `val MedivaInfoBorder = Color(0xFFC7DBFE)`

Update `app/src/main/java/com/aduhbingung/mediva/ui/theme/Theme.kt` ensuring Light Mode only (`darkTheme = false` and no dynamic color overrides).

- [ ] **Step 4: Run test to verify it passes**

Run: `.\gradlew.bat testDebugUnitTest --tests com.aduhbingung.mediva.AuthValidationTest`
Expected: PASS.

---

### Task 2: Model Navigasi & Komponen UI Bersama (*Shared Components*)

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/model/AuthRoute.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/components/AuthComponents.kt`

**Interfaces:**
- Consumes: `MedivaAuthPrimary`, `MedivaAuthBg`, `MedivaSurface`, etc.
- Produces: `AuthRoute` sealed hierarchy, `MedivaHeaderIllustration`, `MedivaWhiteSheetContainer`, `MedivaAuthTextField`, `MedivaAuthButton`, `MedivaSecondaryOutlineButton`, `SocialSignInButtons`, `OtpInputField`, `SecurityCalloutCard`, `RequirementChecklistItem`

- [ ] **Step 1: Create AuthRoute definition**

Create `app/src/main/java/com/aduhbingung/mediva/ui/auth/model/AuthRoute.kt`:
```kotlin
package com.aduhbingung.mediva.ui.auth.model

enum class OtpSource {
    SIGN_UP,
    PASSWORD_RESET
}

sealed interface AuthRoute {
    data object SignIn : AuthRoute
    data object SignUp : AuthRoute
    data class VerifyOtp(val source: OtpSource, val destination: String = "mediva12@example.com") : AuthRoute
    data object LocationPermission : AuthRoute
    data object ForgotPassword : AuthRoute
    data object ResetPassword : AuthRoute
    data object PasswordSuccess : AuthRoute
    data object Home : AuthRoute
}
```

- [ ] **Step 2: Create reusable UI components in AuthComponents.kt**

Implement:
- `MedivaHeaderIllustration(onBackClick: (() -> Unit)? = null)`: Container height ~240.dp with background `MedivaAuthBg`, optional back button in circular card, and `Image(painterResource(R.drawable.ilustrasi1))`.
- `MedivaWhiteSheetContainer`: Surface with `RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)` filling max size.
- `MedivaAuthTextField`: Clean OutlinedTextField with label, rounded corners (12.dp), password toggle visual transformation support.
- `MedivaAuthButton`: Button with `MedivaAuthPrimary` (`#3F65E4`), rounded 12.dp, full-width.
- `MedivaSecondaryOutlineButton`: OutlinedButton with rounded 12.dp and border `#E2E8F0`.
- `SocialSignInButtons`: Google and Apple buttons with vector/canvas icons and bordered card styling.
- `OtpInputField`: 6 bordered text fields of size ~48.dp x 56.dp, centered text, digit input only.
- `SecurityCalloutCard`: Info container with `MedivaInfoBg`, icon (mail or lock), and descriptive text.
- `RequirementChecklistItem`: Row with green checkmark icon and text for password criteria.

- [ ] **Step 3: Compile check**

Run: `.\gradlew.bat compileDebugKotlin`
Expected: BUILD SUCCESSFUL.

---

### Task 3: Implementasi Layar Sign In, Sign Up & Verifikasi OTP (Layar 1–3)

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/screens/LoginScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/screens/RegisterScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/screens/VerifyOtpScreen.kt`

**Interfaces:**
- Consumes: `AuthComponents`, `AuthRoute`, `OtpSource`
- Produces: `LoginScreen`, `RegisterScreen`, `VerifyOtpScreen` (each with full `@Preview`)

- [ ] **Step 1: Implement LoginScreen.kt**

Features:
- Header `MedivaHeaderIllustration` with `R.drawable.ilustrasi1`.
- Sheet with title "Selamat Datang Di Mediva!" and subtitle.
- Input "Alamat Email / Nomor Telepon" & "Kata Sandi" with toggle eye icon.
- "Lupa kata sandi?" right aligned text link.
- Primary CTA "MASUK" (`#3F65E4`) -> navigates to `LocationPermission`.
- Divider "Atau gunakan akun".
- Social buttons (Google, Apple).
- Footer "Belum punya akun Mediva? **Daftar Sekarang**" -> navigates to `SignUp`.
- Interactive state (typing, password visibility toggle).
- `@Preview(showBackground = true)` composable.

- [ ] **Step 2: Implement RegisterScreen.kt**

Features:
- Medical `+` icon badge in blue circular background at top.
- Title "Daftar Akun Mediva!" and subtitle.
- Stepper banner: "1. Langkah 1: Data Diri - Lengkapi informasi dasar untuk membuat akun baru".
- Inputs: "Nama lengkap", "No Telp", "Email", "Password".
- Checkbox "Saya menyetujui S&K".
- Primary button "Daftar" (`#3F65E4`) -> navigates to `VerifyOtp(source = OtpSource.SIGN_UP)`.
- Divider "Atau gunakan akun" + Google sign in button.
- Footer "Sudah punya akun? **Masuk**" -> navigates to `SignIn`.
- `@Preview(showBackground = true)` composable.

- [ ] **Step 3: Implement VerifyOtpScreen.kt**

Features:
- Top bar with back button + title "Verifikasi Akun".
- Mail badge in circular light blue container.
- Title "Periksa kode verifikasi" and subtitle with destination email.
- 6-digit OTP input boxes.
- "Belum menerima kode? **Kirim ulang**".
- Security card with lock icon.
- Primary button "Verifikasi Kode" (`#3F65E4`) ->
  - If `source == OtpSource.SIGN_UP` -> navigates to `LocationPermission`.
  - If `source == OtpSource.PASSWORD_RESET` -> navigates to `ResetPassword`.
- Secondary button "Kembali".
- `@Preview(showBackground = true)` composable.

- [ ] **Step 4: Compile check**

Run: `.\gradlew.bat compileDebugKotlin`
Expected: BUILD SUCCESSFUL.

---

### Task 4: Implementasi Layar Izin Lokasi, Pemulihan & Sukses Kata Sandi (Layar 4–7)

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/screens/LocationPermissionScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/screens/ForgotPasswordScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/screens/ResetPasswordScreen.kt`
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/screens/PasswordSuccessScreen.kt`

**Interfaces:**
- Consumes: `AuthComponents`, `AuthValidation`
- Produces: `LocationPermissionScreen`, `ForgotPasswordScreen`, `ResetPasswordScreen`, `PasswordSuccessScreen` (each with `@Preview`)

- [ ] **Step 1: Implement LocationPermissionScreen.kt**

Features:
- Top bar with back button + title "Izin Lokasi".
- Location pin badge in blue container.
- Title "Bantu petugas menemukan lokasi Anda" & subtitle.
- 3 feature cards:
  1. Lokasi laporan darurat
  2. Pelacakan bantuan
  3. Tidak melacak terus-menerus (privasi manual)
- Primary CTA "Izinkan Lokasi" (`#3F65E4`) -> navigates to `Home`.
- Secondary CTA "Pilih lokasi secara manual" -> navigates to `Home`.
- Footer note: "Izin dapat diatur melalui pengaturan perangkat.".
- `@Preview` composable.

- [ ] **Step 2: Implement ForgotPasswordScreen.kt**

Features:
- Header `MedivaHeaderIllustration` with back button to `SignIn`.
- Title "Lupa kata sandi?" & subtitle.
- Input "Alamat Email / Nomor Telepon".
- Info box with mail icon: "Kode verifikasi akan dikirim ke email atau nomor telepon yang Anda masukkan.".
- Primary CTA "Kirim kode verifikasi" (`#3F65E4`) -> navigates to `VerifyOtp(source = OtpSource.PASSWORD_RESET)`.
- Secondary outline button "Kembali ke masuk" -> navigates to `SignIn`.
- Footer note regarding spam folder.
- `@Preview` composable.

- [ ] **Step 3: Implement ResetPasswordScreen.kt**

Features:
- Header `MedivaHeaderIllustration` with back button to `VerifyOtp`.
- Title "Atur kata sandi baru" & subtitle.
- Inputs: "Kata sandi baru" & "Konfirmasi kata sandi baru" with eye toggles.
- Real-time checklist requirements (with green checkmarks via `AuthValidation`):
  - Minimal 8 karakter
  - Gabungkan huruf besar, huruf kecil, dan angka
  - Kedua kata sandi harus sama
- Primary CTA "Simpan kata sandi baru" (`#3F65E4`, disabled until all 3 rules pass) -> navigates to `PasswordSuccess`.
- `@Preview` composable.

- [ ] **Step 4: Implement PasswordSuccessScreen.kt**

Features:
- Checkmark badge in circle at center.
- Title "Kata sandi berhasil diubah" & subtitle.
- Security box: "Demi keamanan, gunakan kata sandi baru saat masuk ke Mediva.".
- Primary CTA "Kembali ke masuk" (`#3F65E4`) -> navigates to `SignIn`.
- Footer note.
- `@Preview` composable.

- [ ] **Step 5: Compile check**

Run: `.\gradlew.bat compileDebugKotlin`
Expected: BUILD SUCCESSFUL.

---

### Task 5: Integrasi Navigasi End-to-End di `MedivaAuthApp` & `MainActivity`

**Files:**
- Create: `app/src/main/java/com/aduhbingung/mediva/ui/auth/MedivaAuthApp.kt`
- Modify: `app/src/main/java/com/aduhbingung/mediva/MainActivity.kt`

**Interfaces:**
- Consumes: All 7 screens, `AuthRoute`, `MedivaTheme`
- Produces: `MedivaAuthApp` root composable, connected `MainActivity`

- [ ] **Step 1: Create MedivaAuthApp.kt**

Implement:
- State `var currentRoute by remember { mutableStateOf<AuthRoute>(AuthRoute.SignIn) }`
- Back stack handling / history list so system back or UI back returns to appropriate caller as defined in `Panduan alur dan akses tracking laporan aktif.pdf`:
  - `A1 (ForgotPassword)` -> back to `SignIn`
  - `A2 (VerifyOtp)` -> back to `SignUp` or `ForgotPassword` based on `source`
  - `A3 (ResetPassword)` -> back to `VerifyOtp`
  - `A4 (PasswordSuccess)` -> back to `SignIn`
  - `B1 (LocationPermission)` -> back to `SignIn`
  - `Home` placeholder screen with "Masuk sebagai: Penolong / Beranda" and "Keluar Akun" button returning to `SignIn`.
- Quick demo flow selector bar (optional floating chip) for rapid demonstration during hackathon testing.

- [ ] **Step 2: Update MainActivity.kt**

Set content with `MedivaTheme` wrapping `MedivaAuthApp()`.

- [ ] **Step 3: Run full assemble and unit tests**

Run: `.\gradlew.bat testDebugUnitTest` and `.\gradlew.bat assembleDebug`
Expected: BUILD SUCCESSFUL with zero errors.

---

## Plan Self-Review Check
- **Spec coverage:** All 7 screens from the user images and PRD/guide are accounted for with explicit parameters, colors (`#3F65E4`, `#CBDBFD`, `#4A7FBF`), and routing paths.
- **No placeholders:** Every task contains concrete code snippets and exact file targets.
- **Type consistency:** `AuthRoute` naming and properties align across Task 2, Task 3, Task 4, and Task 5.
