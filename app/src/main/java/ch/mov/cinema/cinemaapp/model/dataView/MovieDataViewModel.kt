package ch.mov.cinema.cinemaapp.model

import android.content.Context

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ch.mov.cinema.cinemaapp.model.dao.MoviesDao
import ch.mov.cinema.cinemaapp.model.database.MoviesDatabase
import ch.mov.cinema.cinemaapp.model.entities.Movie

class MovieDataViewModel : ViewModel() {

    var db : MoviesDatabase? = null
    var movies = MutableLiveData<MutableList<Movie>>()

    init{
        movies.value = mutableListOf<Movie>()
    }

    fun initDB(context: Context){
        db = Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, "database-movie"
        ).build()
    }

    suspend fun insertMovies(movies: MutableList<Movie>){
        for(movie in movies){
            db?.movieDao()?.insertAll(movie)
        }
    }
    suspend fun getComingSoon() : MutableList<Movie>?{
        val data = db?.movieDao()?.getComingSoon()?.toMutableList()
        return data
    }
    suspend fun getTop250movies() : MutableList<Movie>?{
        val data = db?.movieDao()?.getTop250movies()?.toMutableList()
        return data
    }

    suspend fun getMostPopularMovies() : MutableList<Movie>?{
        val data = db?.movieDao()?.getMostPopularMovies()?.toMutableList()
        return data
    }

    suspend fun getMostPopularTv() : MutableList<Movie>?{
        val data = db?.movieDao()?.getMostPopularTv()?.toMutableList()
        return data
    }

    suspend fun getInTheaters() : MutableList<Movie>?{
        val data = db?.movieDao()?.getInTheaters()?.toMutableList()
        return data
    }

    suspend fun readMovies() : MutableList<Movie>?{
        val data = db?.movieDao()?.allMovies()?.toMutableList()
        return data
    }
}