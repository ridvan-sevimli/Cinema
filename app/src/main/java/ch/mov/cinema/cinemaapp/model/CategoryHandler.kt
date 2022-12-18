package ch.mov.cinema.cinemaapp.model

class CategoryHandler {

    var categoryIdMap = mapOf("https://imdb-api.com/en/API/Top250Movies/k_c5ew4idg" to "top_250_movies",
        "https://imdb-api.com/en/API/ComingSoon/k_c5ew4idg" to "coming_soon",
        "https://imdb-api.com/en/API/MostPopularMovies/k_c5ew4idg" to "most_popular_movies",
        "https://imdb-api.com/en/API/MostPopularTVs/k_c5ew4idg" to "most_popular_tv",
        "https://imdb-api.com/en/API/InTheaters/k_c5ew4idg" to "in_theaters"
    )


    fun getCategoryIds(): Map<String,String>{
        return categoryIdMap
    }
}