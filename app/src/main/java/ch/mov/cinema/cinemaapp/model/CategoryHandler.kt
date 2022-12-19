package ch.mov.cinema.cinemaapp.model

class CategoryHandler {

    var categoryIdMap = mapOf("https://imdb-api.com/en/API/Top250Movies/k_j38getry" to "top_250_movies",
        "https://imdb-api.com/en/API/ComingSoon/k_j38getry" to "coming_soon",
        "https://imdb-api.com/en/API/MostPopularMovies/k_j38getry" to "most_popular_movies",
        "https://imdb-api.com/en/API/MostPopularTVs/k_j38getry" to "most_popular_tv",
        "https://imdb-api.com/en/API/InTheaters/k_j38getry" to "in_theaters"
    )


    fun getCategoryIds(): Map<String,String>{
        return categoryIdMap
    }
}