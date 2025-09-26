package com.calyrsoft.ucbp1.features.profile.domain.vo

import org.junit.Assert.assertEquals
import org.junit.Test

class PersonNameTest {

    @Test
    fun `crea nombre válido`() {
        val name = PersonName.of("Homero J. Simpson")
        assertEquals("Homero J. Simpson", name.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si es muy corto`() {
        PersonName.of("Ana") // < 5 caracteres
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si no contiene letras`() {
        PersonName.of("123456")
    }
}
