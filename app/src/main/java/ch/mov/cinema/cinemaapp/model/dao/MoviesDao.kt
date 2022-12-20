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

    @Query("SELECT * FROM movies WHERE category ='top_250_movies'")
    fun getTop250movies(): List<Movie>

    @Query("SELECT * FROM movies WHERE category ='coming_soon'")
    fun getComingSoon(): List<Movie>

    @Query("SELECT * FROM movies WHERE category ='most_popular_movies'")
    fun getMostPopularMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE category ='most_popular_tv'")
    fun getMostPopularTv(): List<Movie>

    @Query("SELECT * FROM movies WHERE category ='in_theaters'")
    fun getInTheaters(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: Movie)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg movies: Movie)

}