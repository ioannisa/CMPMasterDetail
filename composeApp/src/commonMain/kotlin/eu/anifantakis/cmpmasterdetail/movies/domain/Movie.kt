package eu.anifantakis.cmpmasterdetail.movies.domain

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate?,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String
)