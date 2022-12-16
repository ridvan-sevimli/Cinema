package ch.mov.cinema.cinemaapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.mov.cinema.cinemaapp.model.dao.MoviesDao
import ch.mov.cinema.cinemaapp.model.entities.Movies

@Database(entities = [Movies::class],version = 1,exportSchema = false)
abstract class MoviesDatabase : RoomDatabase(){

    companion object{
        var moviesDatabase: MoviesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MoviesDatabase {
            if(moviesDatabase != null){
                moviesDatabase = Room.databaseBuilder(
                    context,
                    MoviesDatabase::class.java,
                    "movie.db"
                ).build()
            }
            return moviesDatabase!!
        }
    }

    abstract fun recipeDao(): MoviesDao
}