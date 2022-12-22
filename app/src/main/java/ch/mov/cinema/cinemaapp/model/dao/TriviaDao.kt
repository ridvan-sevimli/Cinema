package ch.mov.cinema.cinemaapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.mov.cinema.cinemaapp.model.entities.Answers
import ch.mov.cinema.cinemaapp.model.entities.Players
import ch.mov.cinema.cinemaapp.model.entities.Questions


@Dao
interface TriviaDao {
    @Query("SELECT * FROM questions ORDER BY id DESC")
    fun allQuestions(): List<Questions>

//    @Query("SELECT * FROM questions WHERE category ='top_250_movies'")
//    fun getTop250movies(): List<Questions>

    @Query("SELECT * FROM questions WHERE category ='mixed'")
    fun getMixed(): List<Questions>

    @Query("SELECT * FROM answers ORDER BY id DESC")
    fun getAnswers(): List<Answers>

    @Query("SELECT * FROM players ORDER BY id DESC")
    fun getPlayers(): List<Players>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg players: Players)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg answers: Answers)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg questions: Questions)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateQuestion(vararg questions: Questions)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlayer(vararg player: Players)

}