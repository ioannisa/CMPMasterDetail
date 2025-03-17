package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: Long?,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String
)