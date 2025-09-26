package com.calyrsoft.ucbp1.features.profile.domain.vo

import org.junit.Assert.assertEquals
import org.junit.Test

class SummaryTextTest {

    @Test
    fun `crea resumen valido`() {
        val s = SummaryText.of("Inspector de seguridad en la planta nuclear.")
        assertEquals("Inspector de seguridad en la planta nuclear.", s.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si es muy corto`() {
        SummaryText.of("abcd") // <= 4
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si no contiene letras`() {
        SummaryText.of("1234567890")
    }

    @Test
    fun `trimea espacios al crear`() {
        val s = SummaryText.of("   Resumen valido   ")
        assertEquals("Resumen valido", s.value)
    }
}
