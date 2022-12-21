package ch.mov.cinema.cinemaapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.mov.cinema.cinemaapp.model.dao.TriviaDao
import ch.mov.cinema.cinemaapp.model.entities.Answers
import ch.mov.cinema.cinemaapp.model.entities.Questions


@Database(entities = [Questions::class,Answers::class],version = 1,exportSchema = false)
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun triviaDao(): TriviaDao

    companion object {
        @Volatile
        private var INSTANCE: TriviaDatabase? = null


        fun getDatabase(context: Context): TriviaDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TriviaDatabase::class.java,
                    "trivia.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}