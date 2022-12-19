package ch.mov.cinema.enums

enum class MainCategoryApi (val category: String){

    COMING_SOON("https://imdb-api.com/en/API/ComingSoon/"),
    TOP_250_MOVIES("https://imdb-api.com/en/API/Top250Movies/"),
    MOST_POPULAR_MOVIES("https://imdb-api.com/en/API/MostPopularMovies/"),
    MOST_POPULAR_TV( "https://imdb-api.com/en/API/MostPopularTVs/"),
    IN_THEATERS("https://imdb-api.com/en/API/InTheaters/"),
}