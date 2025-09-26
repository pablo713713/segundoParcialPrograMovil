package com.calyrsoft.ucbp1.features.profile.domain.vo

import org.junit.Assert.assertEquals
import org.junit.Test

class PhoneNumberTest {

    @Test
    fun `crea telefono valido con simbolos`() {
        val p = PhoneNumber.of("+1 (939) 555-7422")
        assertEquals("+1 (939) 555-7422", p.value)
    }

    @Test
    fun `crea telefono valido con solo digitos minimo 5`() {
        val p = PhoneNumber.of("12345")
        assertEquals("12345", p.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si tiene menos de 5 digitos`() {
        PhoneNumber.of("1234")
    }

    @Test
    fun `trimea espacios al crear`() {
        val p = PhoneNumber.of("   555-12345   ")
        assertEquals("555-12345", p.value)
    }
}
