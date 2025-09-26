package com.calyrsoft.ucbp1.features.profile.domain

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileModel
import com.calyrsoft.ucbp1.features.profile.domain.vo.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileModelTest {

    @Test
    fun `construye model valido con VO`() {
        val model = ProfileModel(
            pathUrl = UrlPath.of("https://site.com/pic.jpg"),
            name = PersonName.of("Homero J. Simpson"),
            email = EmailAddress.of("homero@springfield.com"),
            cellphone = PhoneNumber.of("+1 555 12345"),
            summary = SummaryText.of("Inspector de seguridad en la planta nuclear.")
        )

        assertEquals("https://site.com/pic.jpg", model.pathUrl.value)
        assertEquals("Homero J. Simpson", model.name.value)
        assertEquals("homero@springfield.com", model.email.value)
        assertEquals("+1 555 12345", model.cellphone.value)
        assertEquals("Inspector de seguridad en la planta nuclear.", model.summary.value)
    }

    @Test
    fun `dos modelos con mismos valores son iguales`() {
        val a = ProfileModel(
            pathUrl = UrlPath.of("https://site.com/pic.jpg"),
            name = PersonName.of("Homero J. Simpson"),
            email = EmailAddress.of("homero@springfield.com"),
            cellphone = PhoneNumber.of("+1 555 12345"),
            summary = SummaryText.of("Inspector de seguridad en la planta nuclear.")
        )
        val b = ProfileModel(
            pathUrl = UrlPath.of("https://site.com/pic.jpg"),
            name = PersonName.of("Homero J. Simpson"),
            email = EmailAddress.of("homero@springfield.com"),
            cellphone = PhoneNumber.of("+1 555 12345"),
            summary = SummaryText.of("Inspector de seguridad en la planta nuclear.")
        )
        assertTrue(a == b)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla al construir si algun VO es invalido`() {
        ProfileModel(
            pathUrl = UrlPath.of("https://site.com/pic.jpg"),
            name = PersonName.of("Ana"), // invalido (<5 chars)
            email = EmailAddress.of("homero@springfield.com"),
            cellphone = PhoneNumber.of("+1 555 12345"),
            summary = SummaryText.of("Resumen valido")
        )
    }
}
