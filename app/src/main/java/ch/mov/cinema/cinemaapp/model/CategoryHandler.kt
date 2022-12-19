package ch.mov.cinema.cinemaapp.model

import ch.mov.cinema.cinemaapp.model.enums.MainCategoryApi

class CategoryHandler {

    var categoryIdMap = mapOf(
        MainCategoryApi.COMING_SOON.category to "coming_soon",
        MainCategoryApi.TOP_250_MOVIES.category to "top_250_movies",
        MainCategoryApi.MOST_POPULAR_MOVIES.category to "most_popular_movies",
        MainCategoryApi.MOST_POPULAR_TV.category  to "most_popular_tv",
        MainCategoryApi.IN_THEATERS.category  to "in_theaters"
    )

    fun getCategoryIds(): Map<String,String>{
        return categoryIdMap
    }
}