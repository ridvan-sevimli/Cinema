package ch.mov.cinema.cinemaapp.model.entities

data class Question (val q_id: Int, val category: String, val question: String, val poster: String,val isAnswered: Boolean, val answer: String)