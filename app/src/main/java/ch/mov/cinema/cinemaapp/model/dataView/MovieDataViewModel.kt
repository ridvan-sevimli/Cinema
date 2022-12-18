package ch.mov.cinema.cinemaapp.model

import android.content.Context

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
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

    suspend fun insertMovies(){
        db?.movieDao()?.insertAll(

        )
    }
    suspend fun readMovies(){
        val data = db?.movieDao()?.allMovies()?.toMutableList()
        movies.postValue(data!!)
    }
}