package ch.mov.cinema.cinemaapp.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * This entity is used to store players
 */
@Entity(tableName = "Players")
data class Players(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name="Name")
    var Name : String?,

    @ColumnInfo(name="points")
    var points : Int,

): Serializable
