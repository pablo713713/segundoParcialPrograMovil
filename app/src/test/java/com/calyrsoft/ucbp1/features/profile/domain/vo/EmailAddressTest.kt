package com.calyrsoft.ucbp1.features.profile.domain.vo

import org.junit.Assert.assertEquals
import org.junit.Test

class EmailAddressTest {

    @Test
    fun `crea email valido`() {
        val email = EmailAddress.of("usuario@mail.com")
        assertEquals("usuario@mail.com", email.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si es muy corto`() {
        EmailAddress.of("a@b") // < 5 chars
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si no tiene formato de email`() {
        EmailAddress.of("usuario-sinarroba.com")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si tiene espacios`() {
        EmailAddress.of("user name@mail.com")
    }
}
