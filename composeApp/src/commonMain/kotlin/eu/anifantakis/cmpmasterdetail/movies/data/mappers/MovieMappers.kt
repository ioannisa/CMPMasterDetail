package eu.anifantakis.cmpmasterdetail.movies.data.mappers

import eu.anifantakis.cmpmasterdetail.core.domain.util.toDate
import eu.anifantakis.cmpmasterdetail.core.domain.util.toLocalDate
import eu.anifantakis.cmpmasterdetail.core.domain.util.toLong
import eu.anifantakis.cmpmasterdetail.movies.data.database.MovieEntity
import eu.anifantakis.cmpmasterdetail.movies.data.networking.dto.MovieDto
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        releaseDate = this.releaseDate.toLocalDate(),
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        releaseDate = this.releaseDate?.toLong(),
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        releaseDate =   this.releaseDate?.toDate(),
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}