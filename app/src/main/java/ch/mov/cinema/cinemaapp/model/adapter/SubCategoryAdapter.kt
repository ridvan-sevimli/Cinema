package ch.mov.cinema.cinemaapp.model.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ch.mov.cinema.DetailFragment
import ch.mov.cinema.HomeFragment
import ch.mov.cinema.R
import ch.mov.cinema.cinemaapp.model.entities.Movie
import ch.mov.cinema.databinding.FragmentHomeBinding
import ch.mov.cinema.databinding.ItemRvSubCategoryBinding
import com.squareup.picasso.Picasso


class SubCategoryAdapter() : RecyclerView.Adapter<SubCategoryAdapter.MovieViewHolder>(){


    var listener : OnItemClickListener? = null
    var arrSubCategory = ArrayList<Movie>()

    class MovieViewHolder(val binding: ItemRvSubCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(appInfo: Movie) {
            binding.root.setOnClickListener {

            }
        }
    }

    fun setData(arrData : List<Movie>){
        arrSubCategory = arrData as ArrayList<Movie>
    }

    fun setClickListener(listener2: OnItemClickListener){
        listener = listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding = ItemRvSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(arrSubCategory[position])
        with(holder){
            with(arrSubCategory[position]) {
                binding.tvMovieName.text = arrSubCategory[position].movieName
                if(arrSubCategory[position].imgPath != "https://imdb-api.com/images/128x176/nopicture.jpg"){
                    Picasso.get()
                        .load(arrSubCategory[position].imgPath).into(binding.imgMovie)
                }else{
                    binding.imgMovie.setImageResource(R.drawable.default_poster)
                }
                holder.itemView.rootView.setOnClickListener {
                    arrSubCategory[position].id.toString()?.let { it1 -> listener!!.onClicked(it1) }
                }

            }
        }
    }

    interface OnItemClickListener{
        fun onClicked(categoryName:String)
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

}