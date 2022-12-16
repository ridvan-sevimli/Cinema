package ch.mov.cinema.cinemaapp.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.mov.cinema.cinemaapp.model.entities.Movies
import ch.mov.cinema.databinding.ItemRvSubCategoryBinding
import com.squareup.picasso.Picasso

class SubCategoryAdapter() : RecyclerView.Adapter<SubCategoryAdapter.MovieViewHolder>(){

    var arrSubCategory = ArrayList<Movies>()

    class MovieViewHolder(val binding: ItemRvSubCategoryBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(arrData : List<Movies>){
        arrSubCategory = arrData as ArrayList<Movies>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemRvSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder){
            with(arrSubCategory[position]) {
                binding.tvDishName.text = arrSubCategory[position].dishName
                Picasso.get()
                    .load(arrSubCategory[position].dishImgPath).into(binding.imgDish)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }
}