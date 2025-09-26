package com.calyrsoft.ucbp1.features.profile.domain.usecase

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileModel
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.vo.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProfileUseCaseTest {

    @Test
    fun `retorna success luego del delay`() = runTest {
        val repo = mockk<IProfileRepository>()
        val expected = ProfileModel(
            pathUrl = UrlPath.of("https://site.com/pic.jpg"),
            name = PersonName.of("Homero J. Simpson"),
            email = EmailAddress.of("homero@springfield.com"),
            cellphone = PhoneNumber.of("+1 555 12345"),
            summary = SummaryText.of("Inspector de seguridad en la planta nuclear.")
        )
        every { repo.fetchData() } returns Result.success(expected)

        val usecase = GetProfileUseCase(repo)

        val deferred = async { usecase.invoke() } // contiene un delay(3000)
        advanceTimeBy(3000)                       // avanzamos el tiempo virtual del test
        val result = deferred.await()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `propaga failure luego del delay`() = runTest {
        val repo = mockk<IProfileRepository>()
        every { repo.fetchData() } returns Result.failure(Exception("boom"))

        val usecase = GetProfileUseCase(repo)

        val deferred = async { usecase.invoke() }
        advanceTimeBy(3000)
        val result = deferred.await()

        assertTrue(result.isFailure)
        assertEquals("boom", result.exceptionOrNull()?.message)
    }
}
