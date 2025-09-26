package com.calyrsoft.ucbp1.features.profile.data.repository

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileModel
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.vo.EmailAddress
import com.calyrsoft.ucbp1.features.profile.domain.vo.PersonName
import com.calyrsoft.ucbp1.features.profile.domain.vo.PhoneNumber
import com.calyrsoft.ucbp1.features.profile.domain.vo.SummaryText
import com.calyrsoft.ucbp1.features.profile.domain.vo.UrlPath

class ProfileRepository : IProfileRepository {
    override fun fetchData(): Result<ProfileModel> = runCatching {
        ProfileModel(
            name = PersonName.of("Pablo La Torre"),
            email = EmailAddress.of("pablito.latower@springfieldmail.com"),
            cellphone = PhoneNumber.of("+1 (939) 555-7422"),
            pathUrl = UrlPath.of("https://www.viaempresa.cat/uploads/s1/43/99/69/homer.jpg"),
            summary = SummaryText.of("Estudiante Ucb interesado en pasar disp moviles sin morir en el intento :).")
        )
    }
}
