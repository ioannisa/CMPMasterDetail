package eu.anifantakis.cmpmasterdetail.core.domain.util

import kotlinx.datetime.LocalDate

expect fun String.toLocalDate(format: String = "yyyy-MM-dd"): LocalDate?
expect fun LocalDate.toFormattedString(format: String = "yyyy-MM-dd"): String