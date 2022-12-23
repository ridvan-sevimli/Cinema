package ch.mov.cinema.cinemaapp.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
/**
 * This entity is used to store questions in to DB
 */
@Entity(tableName = "Questions")
data class Questions (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name="category")
    var category : String?,

    @ColumnInfo(name="question")
    var questions : String?,

    @ColumnInfo(name= "poster")
    var poster : String?,

    @ColumnInfo(name= "isAnswered")
    var isAnswered : Boolean?,

    @ColumnInfo(name= "answer")
    var answer : String?

): Serializable
