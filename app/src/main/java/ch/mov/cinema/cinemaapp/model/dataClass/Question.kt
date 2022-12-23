package ch.mov.cinema.cinemaapp.model.dataClass
/**
 * This data class is used to store the data from the json file
 */
data class Question (val q_id: Int, val category: String, val question: String, val poster: String,val isAnswered: Boolean, val answer: String)