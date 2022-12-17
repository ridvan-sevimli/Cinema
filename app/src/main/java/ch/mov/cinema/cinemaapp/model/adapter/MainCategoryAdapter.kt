package ch.mov.cinema.cinemaapp.model.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.mov.cinema.R
import ch.mov.cinema.cinemaapp.model.entities.Movies
import ch.mov.cinema.databinding.ItemRvMainCategoryBinding
import com.squareup.picasso.Picasso
import java.io.File

class MainCategoryAdapter() : RecyclerView.Adapter<MainCategoryAdapter.MovieViewHolder>(){

    var listener : OnItemClickListener? = null
    var arrMainCategory = ArrayList<Movies>()

    class MovieViewHolder(val binding: ItemRvMainCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(appInfo: Movies) {
            binding.root.setOnClickListener {
               
            }
        }
    }

    fun setData(arrData : List<Movies>){
        arrMainCategory = arrData as ArrayList<Movies>
    }

    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemRvMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(arrMainCategory[position]) // <<< Change here
        with(holder){
            with(arrMainCategory[position]) {
                binding.tvDishName.text = arrMainCategory[position].dishName
                arrMainCategory[position].dishImgPath?.toInt()?.let { binding.imgMov.setImageResource(it) }

                holder.itemView.rootView.setOnClickListener {
                    arrMainCategory[position].dishName?.let { it1 -> listener!!.onClicked(it1) }
                }

//                Picasso.get()
//                    .load(arrMainCategory[position].dishImgPath).into(binding.imgDish)
            }
        }
    }

    interface OnItemClickListener{
        fun onClicked(categoryName:String)
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }
}