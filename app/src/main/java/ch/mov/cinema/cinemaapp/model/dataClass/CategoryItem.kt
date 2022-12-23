package ch.mov.cinema.cinemaapp.model.dataClass

import ch.mov.cinema.cinemaapp.model.dataClass.Category

/**
 * This data class is used to store the data from the json file
 * used by Klaxon
 */
data class CategoryItem(val maincategories: MutableList<Category>)