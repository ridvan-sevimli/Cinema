package ch.mov.cinema.cinemaapp.model

import ch.mov.cinema.enums.MainCategoryApi

class CategoryHandler {

    var categoryIdMap = mapOf(
        ch.mov.cinema.enums.MainCategoryApi.COMING_SOON.category to "coming_soon",
        ch.mov.cinema.enums.MainCategoryApi.TOP_250_MOVIES.category to "top_250_movies",
        ch.mov.cinema.enums.MainCategoryApi.MOST_POPULAR_MOVIES.category to "most_popular_movies",
        ch.mov.cinema.enums.MainCategoryApi.MOST_POPULAR_TV.category  to "most_popular_tv",
        ch.mov.cinema.enums.MainCategoryApi.IN_THEATERS.category  to "in_theaters"
    )

    fun getCategoryIds(): Map<String,String>{
        return categoryIdMap
    }
}