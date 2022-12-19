package ch.mov.cinema.cinemaapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.mov.cinema.cinemaapp.model.dao.MoviesDao
import ch.mov.cinema.cinemaapp.model.entities.Movie


@Database(entities = [Movie::class],version = 1,exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null


        fun getDatabase(context: Context): MoviesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MoviesDatabase::class.java,
                    "movie.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}