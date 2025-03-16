package eu.anifantakis.cmpmasterdetail.movies.data.mappers

import eu.anifantakis.cmpmasterdetail.movies.data.networking.dto.MovieDto
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import kotlinx.datetime.LocalDate

fun MovieDto.toMovie(): Movie {

    val localDate = runCatching { LocalDate.parse(this.releaseDate) }.getOrNull()

    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        releaseDate = localDate,
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}