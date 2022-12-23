package ch.mov.cinema.cinemaapp.model.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.mov.cinema.R
import ch.mov.cinema.cinemaapp.model.entities.Questions
import ch.mov.cinema.databinding.ItemRvSubCategoryBinding
import com.squareup.picasso.Picasso

/**
 * Adapter for Sub Category.
 * Adds onClickListener to the resyclerview items.
 * Handles individual elements setting text and icon
 */
class SubCategoryAdapter() : RecyclerView.Adapter<SubCategoryAdapter.QuestionsViewHolder>(){


    var listener : OnItemClickListener? = null
    var arrSubCategory = ArrayList<Questions>()

    class QuestionsViewHolder(val binding: ItemRvSubCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(appInfo: Questions) {
            binding.root.setOnClickListener {

            }
        }
    }

    fun setData(arrData : List<Questions>){
        arrSubCategory = arrData as ArrayList<Questions>
    }

    fun setClickListener(listener2: OnItemClickListener){
        listener = listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {

        val binding = ItemRvSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        holder.bind(arrSubCategory[position])
        with(holder){
            with(arrSubCategory[position]) {
                binding.tvMovieName.text = arrSubCategory[position].questions
                if(arrSubCategory[position].poster != "no_poster"){
                    Picasso.get()
                        .load(arrSubCategory[position].poster).into(binding.imgMovie)
                }else{
                    binding.imgMovie.setImageResource(R.drawable.default_poster)
                }
                holder.itemView.rootView.setOnClickListener {
                    //arrSubCategory[position].imgPath.toString()?.let { it1 -> listener!!.onClicked(it1) }
                    var imagePath = arrSubCategory[position].poster.toString()
                    var questionId = arrSubCategory[position].id.toString()
                    var category = arrSubCategory[position].category.toString()
                    listener!!.onClicked(imagePath,questionId,category)
                }

            }
        }
    }

    interface OnItemClickListener{
        fun onClicked(imagePath:String, questionId: String,category: String)
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

}