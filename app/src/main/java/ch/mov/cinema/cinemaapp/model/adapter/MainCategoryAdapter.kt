package ch.mov.cinema.cinemaapp.model.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.mov.cinema.cinemaapp.model.entities.Category
import ch.mov.cinema.databinding.ItemRvMainCategoryBinding
import ch.mov.cinema.enums.TriviaKeyIds


class MainCategoryAdapter() : RecyclerView.Adapter<MainCategoryAdapter.CategoryViewHolder>(){

    var listener : OnItemClickListener? = null
    var arrMainCategory = ArrayList<Category>()
    var currentPlayerId = 0
    var selectedCategory = ""
    class CategoryViewHolder(val binding: ItemRvMainCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(appInfo: Category) {
            binding.root.setOnClickListener {

            }
        }
    }

    fun setData(arrData : List<Category>){
        arrMainCategory = arrData as ArrayList<Category>
    }

    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemRvMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val settings = parent.context?.getSharedPreferences("prefsfile", Context.MODE_PRIVATE)
        currentPlayerId = settings?.getInt(TriviaKeyIds.CURRENT_PLAYER_ID.triviaKey, 0)!!
        selectedCategory = settings?.getString(TriviaKeyIds.SELECTED_CATEGORY.triviaKey,"Mixed")!!
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(arrMainCategory[position])
        with(holder){
            with(arrMainCategory[position]) {
                binding.tvCategoryName.text = arrMainCategory[position].title
                arrMainCategory[position].icon?.toInt()?.let { binding.imgMov.setImageResource(it) }

                if(currentPlayerId == 2){
                    if(arrMainCategory[position].title == selectedCategory){
                        arrMainCategory[position].title?.let { it1 -> listener!!.onClicked(it1) }
                    }else{
                        binding.categoryCard.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))
                    }
                }else {
                    holder.itemView.rootView.setOnClickListener {
                        arrMainCategory[position].title?.let { it1 -> listener!!.onClicked(it1) }
                    }
                }

//                Picasso.get()
//                    .load(arrMainCategory[position].dishImgPath).into(binding.imgDish)
            }
        }
    }

    fun getCurrentPlayer() : Int?{

        return (currentPlayerId)
    }

    interface OnItemClickListener{
        fun onClicked(categoryName:String)
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }
}