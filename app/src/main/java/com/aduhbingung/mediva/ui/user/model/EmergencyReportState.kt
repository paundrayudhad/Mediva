package com.aduhbingung.mediva.ui.user.model

enum class IncidentType(val displayName: String, val emoji: String) {
    KECELAKAAN("Kecelakaan", "🚗"),
    KONDISI_MEDIS("Kondisi Medis", "🧠"),
    KEBAKARAN("Kebakaran", "🔥"),
    LAINNYA("Lainnya", "＋")
}

data class EmergencyReportState(
    val incidentType: IncidentType = IncidentType.KECELAKAAN,
    val hasPhotoAttachment: Boolean = true,
    val photoUri: String? = null,
    val locationAddress: String = "Jl. Slamet Riyadi, Surakarta",
    val locationAccuracy: String = "Akurat (6 meter) • Lokasi saat ini",
    val incidentDetails: String = "Tabrakan sepeda motor, satu korban tergeletak di jalan raya, membutuhkan bantuan segera.",
    val victimCount: Int = 1,
    val reportTime: String = "09:41 WIB",
    val reportId: String = "#MDV-2409"
) {
    fun incrementVictims(): EmergencyReportState =
        copy(victimCount = victimCount + 1)

    fun decrementVictims(): EmergencyReportState =
        copy(victimCount = if (victimCount > 1) victimCount - 1 else 1)

    fun isLocationValid(): Boolean = locationAddress.isNotBlank()

    companion object {
        fun isSlideCompleted(fraction: Float): Boolean = fraction >= 0.90f
    }
}
