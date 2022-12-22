package ch.mov.cinema.cinemaapp.model

import android.content.Context

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ch.mov.cinema.cinemaapp.model.database.TriviaDatabase
import ch.mov.cinema.cinemaapp.model.entities.Answers
import ch.mov.cinema.cinemaapp.model.entities.Players
import ch.mov.cinema.cinemaapp.model.entities.Questions

class TriviaDataViewModel : ViewModel() {

    var db : TriviaDatabase? = null
    var questions = MutableLiveData<MutableList<Questions>>()

    init{
        questions.value = mutableListOf<Questions>()
    }

    fun initDB(context: Context){
        db = Room.databaseBuilder(
            context,
            TriviaDatabase::class.java, "database-trivia"
        ).build()
    }

    suspend fun insertQuestions(questions: MutableList<Questions>){
        for(question in questions){
            db?.triviaDao()?.insertAll(question)
        }
    }

    suspend fun insertAnswers(answers: MutableList<Answers>){
        for(answer in answers){
            db?.triviaDao()?.insertAll(answer)
        }
    }

    suspend fun updateQuestion(answers: Questions){
            db?.triviaDao()?.updateQuestion(answers)
    }


    suspend fun getMixed() : MutableList<Questions>?{
        val data = db?.triviaDao()?.getMixed()?.toMutableList()
        return data
    }

    suspend fun insertPlayer(players: MutableList<Players>){
        for(player in players){
            db?.triviaDao()?.insertAll(player)
        }
    }

    suspend fun getPlayers() : MutableList<Players>?{
        val data = db?.triviaDao()?.getPlayers()?.toMutableList()
        return data
    }


    suspend fun updatePlayers(player: Players){
        db?.triviaDao()?.updatePlayer(player)
    }

    suspend fun getAnswers() : MutableList<Answers>?{
        val data = db?.triviaDao()?.getAnswers()?.toMutableList()
        return data
    }

    suspend fun clearQuestionsDb(){
        db?.triviaDao()?.clearQuestionsDb()
    }

}