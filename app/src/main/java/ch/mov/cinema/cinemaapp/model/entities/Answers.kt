package ch.mov.cinema.cinemaapp.model.entities



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * This entity is used to store answers to the questions
 */
@Entity(tableName = "Answers")
data class Answers (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name="type")
    var type : String?,

    @ColumnInfo(name= "a")
    var a : String?,

    @ColumnInfo(name= "b")
    var b : String?,

    @ColumnInfo(name= "c")
    var c : String?,

    @ColumnInfo(name= "d")
    var d : String?,

    @ColumnInfo(name= "answers")
    var answers : String?,
): Serializable
