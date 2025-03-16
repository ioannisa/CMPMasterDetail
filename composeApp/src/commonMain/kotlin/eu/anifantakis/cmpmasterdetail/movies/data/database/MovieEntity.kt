package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate?,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String
)