package com.calyrsoft.ucbp1.features.profile.domain.model

import com.calyrsoft.ucbp1.features.profile.domain.vo.EmailAddress
import com.calyrsoft.ucbp1.features.profile.domain.vo.PersonName
import com.calyrsoft.ucbp1.features.profile.domain.vo.PhoneNumber
import com.calyrsoft.ucbp1.features.profile.domain.vo.SummaryText
import com.calyrsoft.ucbp1.features.profile.domain.vo.UrlPath

data class ProfileModel(
    val pathUrl: UrlPath,
    val name: PersonName,
    val email: EmailAddress,
    val cellphone: PhoneNumber,
    val summary: SummaryText
)
