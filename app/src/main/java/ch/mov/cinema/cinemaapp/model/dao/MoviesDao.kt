package ch.mov.cinema.cinemaapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.mov.cinema.cinemaapp.model.entities.Movie


@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY id DESC")
    fun allMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(recipes: Movie)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg recipes: Movie)

}