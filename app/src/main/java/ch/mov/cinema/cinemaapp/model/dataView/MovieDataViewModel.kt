package ch.mov.cinema.cinemaapp.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ch.mov.cinema.cinemaapp.model.database.MoviesDatabase
import ch.mov.cinema.cinemaapp.model.entities.Movies

class MovieDataViewModel : ViewModel() {

    var db : MoviesDatabase? = null
    var recipes = MutableLiveData<MutableList<Movies>>()

    init{
        recipes.value = mutableListOf<Movies>()
    }

    fun initDB(context: Context){
        db = Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, "database-movie"
        ).build()
    }

    suspend fun insertMovies(){
        db?.recipeDao()?.insertAll(

        )
    }
    suspend fun readMovies(){
        val data = db?.recipeDao()?.allMovies()?.toMutableList()
        recipes.postValue(data!!)
    }
}