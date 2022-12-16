package ch.mov.cinema.cinemaapp.model.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.mov.cinema.R
import ch.mov.cinema.cinemaapp.model.entities.Movies
import ch.mov.cinema.databinding.ItemRvMainCategoryBinding
import com.squareup.picasso.Picasso
import java.io.File

class MainCategoryAdapter() : RecyclerView.Adapter<MainCategoryAdapter.MovieViewHolder>(){

    var arrMainCategory = ArrayList<Movies>()

    class MovieViewHolder(val binding: ItemRvMainCategoryBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(arrData : List<Movies>){
        arrMainCategory = arrData as ArrayList<Movies>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemRvMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder){
            with(arrMainCategory[position]) {
                binding.tvDishName.text = arrMainCategory[position].dishName
                arrMainCategory[position].dishImgPath?.toInt()?.let { binding.imgMov.setImageResource(it) }
//                Picasso.get()
//                    .load(arrMainCategory[position].dishImgPath).into(binding.imgDish)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }
}