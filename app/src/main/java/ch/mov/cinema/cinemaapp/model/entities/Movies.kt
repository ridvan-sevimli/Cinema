package ch.mov.cinema.cinemaapp.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Movies")
data class Movies (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name= "dishName")
    var dishName : String?,

    @ColumnInfo(name= "dishImgPath")
    var dishImgPath : String?

): Serializable