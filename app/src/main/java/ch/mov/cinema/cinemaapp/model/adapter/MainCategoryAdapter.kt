package ch.mov.cinema.cinemaapp.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.mov.cinema.cinemaapp.model.entities.Movie
import ch.mov.cinema.databinding.ItemRvMainCategoryBinding
import com.squareup.picasso.Picasso


class MainCategoryAdapter() : RecyclerView.Adapter<MainCategoryAdapter.MovieViewHolder>(){

    var listener : OnItemClickListener? = null
    var arrMainCategory = ArrayList<Movie>()

    class MovieViewHolder(val binding: ItemRvMainCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(appInfo: Movie) {
            binding.root.setOnClickListener {
               
            }
        }
    }

    fun setData(arrData : List<Movie>){
        arrMainCategory = arrData as ArrayList<Movie>
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
                binding.tvCategoryName.text = arrMainCategory[position].movieName
                arrMainCategory[position].imgPath?.toInt()?.let { binding.imgMov.setImageResource(it) }

                holder.itemView.rootView.setOnClickListener {
                    arrMainCategory[position].movieName?.let { it1 -> listener!!.onClicked(it1) }
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