package ch.mov.cinema.cinemaapp.model

import android.content.Context

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ch.mov.cinema.cinemaapp.model.database.TriviaDatabase
import ch.mov.cinema.cinemaapp.model.entities.Questions

class TriviaDataViewModel : ViewModel() {

    var db : TriviaDatabase? = null
    var movies = MutableLiveData<MutableList<Questions>>()

    init{
        movies.value = mutableListOf<Questions>()
    }

    fun initDB(context: Context){
        db = Room.databaseBuilder(
            context,
            TriviaDatabase::class.java, "database-trivia"
        ).build()
    }

    suspend fun insertMovies(movies: MutableList<Questions>){
        for(movie in movies){
            db?.triviaDao()?.insertAll(movie)
        }
    }
    suspend fun getComingSoon() : MutableList<Questions>?{
        val data = db?.triviaDao()?.getComingSoon()?.toMutableList()
        return data
    }
    suspend fun getTop250movies() : MutableList<Questions>?{
        val data = db?.triviaDao()?.getTop250movies()?.toMutableList()
        return data
    }

    suspend fun getMostPopularMovies() : MutableList<Questions>?{
        val data = db?.triviaDao()?.getMostPopularMovies()?.toMutableList()
        return data
    }

    suspend fun getMostPopularTv() : MutableList<Questions>?{
        val data = db?.triviaDao()?.getMostPopularTv()?.toMutableList()
        return data
    }

    suspend fun getInTheaters() : MutableList<Questions>?{
        val data = db?.triviaDao()?.getInTheaters()?.toMutableList()
        return data
    }

    suspend fun readMovies() : MutableList<Questions>?{
        val data = db?.triviaDao()?.allMovies()?.toMutableList()
        return data
    }
}