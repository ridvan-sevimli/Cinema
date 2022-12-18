package ch.mov.cinema.cinemaapp.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Movies")
data class Movie (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name="category")
    var category : String?,

    @ColumnInfo(name= "movieName")
    var movieName : String?,

    @ColumnInfo(name= "imgPath")
    var imgPath : String?

): Serializable