package ch.mov.cinema.cinemaapp.model.enums

enum class MainCategoryApi (val category: String){
    COMING_SOON("https://imdb-api.com/en/API/ComingSoon/k_j38getry"),
    TOP_250_MOVIES("https://imdb-api.com/en/API/Top250Movies/k_j38getry"),
    MOST_POPULAR_MOVIES("https://imdb-api.com/en/API/MostPopularMovies/k_j38getry"),
    MOST_POPULAR_TV( "https://imdb-api.com/en/API/MostPopularTVs/k_j38getry"),
    IN_THEATERS("https://imdb-api.com/en/API/InTheaters/k_j38getry"),
}