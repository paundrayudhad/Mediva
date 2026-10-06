package com.aduhbingung.mediva

import com.aduhbingung.mediva.ui.user.model.EmergencyReportState
import com.aduhbingung.mediva.ui.user.model.IncidentType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmergencyReportTest {
    @Test
    fun testDefaultReportState() {
        val state = EmergencyReportState()
        assertEquals(IncidentType.KECELAKAAN, state.incidentType)
        assertEquals(1, state.victimCount)
        assertEquals("Jl. Slamet Riyadi, Surakarta", state.locationAddress)
        assertTrue(state.isLocationValid())
    }

    @Test
    fun testVictimCountAdjustment() {
        var state = EmergencyReportState(victimCount = 1)
        state = state.incrementVictims()
        assertEquals(2, state.victimCount)
        state = state.decrementVictims()
        assertEquals(1, state.victimCount)
        // Should not drop below 1
        state = state.decrementVictims()
        assertEquals(1, state.victimCount)
    }

    @Test
    fun testSlideToConfirmValidation() {
        assertFalse(EmergencyReportState.isSlideCompleted(0.2f))
        assertFalse(EmergencyReportState.isSlideCompleted(0.85f))
        assertTrue(EmergencyReportState.isSlideCompleted(0.92f))
        assertTrue(EmergencyReportState.isSlideCompleted(1.0f))
    }
}
