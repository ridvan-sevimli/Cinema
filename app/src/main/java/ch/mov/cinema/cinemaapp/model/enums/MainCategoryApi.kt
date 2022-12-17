package ch.mov.cinema.cinemaapp.model.enums

enum class MainCategoryApi (val category: String){
    COMING_SOON("https://imdb-api.com/en/API/ComingSoon/k_dpwxdc3v"),
    TOP_250_MOVIES("https://imdb-api.com/en/API/Top250TVs/k_dpwxdc3v"),
    MOST_POPULAR_MOVIES("https://imdb-api.com/en/API/MostPopularMovies/k_dpwxdc3v"),
    MOST_POPULAR_TV("https://imdb-api.com/en/API/MostPopularTVs/k_dpwxdc3v"),
    IN_THEATERS("https://imdb-api.com/en/API/InTheaters/k_dpwxdc3v"),
    BOX_OFFICE("https://imdb-api.com/en/API/BoxOffice/k_dpwxdc3v"),
    BOX_OFFICE_ALL_TIME("https://imdb-api.com/en/API/BoxOfficeAllTime/k_dpwxdc3v")
}