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
            Movie(1,"title","https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,12,128,176_AL_.jpg")
        )
    }
    suspend fun readMovies() : MutableList<Movie>?{
        val data = db?.movieDao()?.allMovies()?.toMutableList()
        return data
    }
}