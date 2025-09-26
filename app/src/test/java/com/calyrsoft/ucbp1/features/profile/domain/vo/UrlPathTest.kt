package com.calyrsoft.ucbp1.features.profile.domain.vo

import org.junit.Assert.assertEquals
import org.junit.Test

class UrlPathTest {

    @Test
    fun `crea url https valida`() {
        val u = UrlPath.of("https://site.com/img.jpg")
        assertEquals("https://site.com/img.jpg", u.value)
    }

    @Test
    fun `crea url http valida`() {
        val u = UrlPath.of("http://example.org")
        assertEquals("http://example.org", u.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si es muy corta`() {
        UrlPath.of("http")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si no empieza con http o https`() {
        UrlPath.of("ftp://server.com/file")
    }

    @Test
    fun `trimea espacios al crear`() {
        val u = UrlPath.of("   https://a.com/pic.png   ")
        assertEquals("https://a.com/pic.png", u.value)
    }
}
