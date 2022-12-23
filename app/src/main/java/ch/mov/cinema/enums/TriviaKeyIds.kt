package ch.mov.cinema.enums


/**
 * Used for Shared Preferences to write and read data in different fragments
 */
enum class TriviaKeyIds(val triviaKey: String) {
    POSTER_PATH("POSTER_PATH"),
    QUESTION_ID("QUESTION_ID"),
    CATEGORY_ID("CATEGORY_ID"),
    CURRENT_PLAYER_ID("PLAYER_ID"),
    CURRENT_PLAYER_POINT("PLAYER_POINT"),
    SELECTED_CATEGORY("SELECTED_CATEGORY_ID")
}