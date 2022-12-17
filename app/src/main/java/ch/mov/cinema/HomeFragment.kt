package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ch.mov.cinema.cinemaapp.model.MovieDataViewModel
import ch.mov.cinema.cinemaapp.model.adapter.MainCategoryAdapter
import ch.mov.cinema.cinemaapp.model.adapter.SubCategoryAdapter
import ch.mov.cinema.cinemaapp.model.entities.*
import ch.mov.cinema.databinding.FragmentHomeBinding
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var arrMainCategory = ArrayList<Movies>()
    var arrSubCategory = ArrayList<Movies>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         *  my implementation
         */

        val model: MovieDataViewModel by activityViewModels()
        model.initDB(requireContext())

        lifecycleScope.launchWhenStarted{
            withContext(Dispatchers.Default){
                model.readMovies()
            }
        }

        /**
         * Test API
         */

        testAPISub()


        initializeCategories(requireContext())

        var mainCategoryAdapter = MainCategoryAdapter()
        mainCategoryAdapter.setData(arrMainCategory)
        mainCategoryAdapter.setClickListener(onCLicked)
        binding.rvMainCategory.adapter = mainCategoryAdapter
        binding.rvMainCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        var subCategoryAdapter = SubCategoryAdapter()
        subCategoryAdapter.setData(arrSubCategory)
        binding.rvSubCategory.adapter = subCategoryAdapter
        binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)









        var text :String
        val url = "https://imdb-api.com/en/API/ComingSoon/k_dpwxdc3v"
        val url2 = "https://www.themealdb.com/api/json/v1/1/categories.php"
//        var categories :Items? = null
        /**
         * Actual API

        //        val requestQueue = getMealDataFromDb.newRequestQueue(requireContext())
        //        val request = StringRequest(Request.Method.GET, url, { response ->
        //           var categories = Klaxon().parse<Items>(response)
        //                for(category in categories?.items!!){
        //                    var id : Int = category.id.subSequence(2,category.id.length).toString().toInt()
        //                    arrSubCategory.add(Recipes(id,category.title,category.image))
        //                }
        //            mainCategoryAdapter.setData(arrMainCategory)
        //            subCategoryAdapter.setData(arrSubCategory)
        //
        //            binding.rvSubCategory.adapter = subCategoryAdapter
        //            binding.rvMainCategory.adapter = mainCategoryAdapter
        //        },
        //            {
        //
        //            })
        //
        //        requestQueue.add(request)
         */

//        model.recipes.observe(viewLifecycleOwner,
//            // note that the observer sends the new value as parameter
//            Observer<MutableList<Recipes>>{newVal ->
////                adapter?.clear()
////                adapter?.addAll(newVal) // addAll will call notifyDatasetChanged
//
//            })


        /**
         *  my implementation
         */

    }

    private val onCLicked  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            binding.textViewCategory.text = categoryName
            getMealDataFromDb("title")
        }
    }

    private fun getMealDataFromDb(categoryName:String){

            var subCategoryAdapter = SubCategoryAdapter()
            //var cat = RecipeDatabase.getDatabase(this@HomeFragment).recipeDao().getSpecificMealList(categoryName)
            arrSubCategory =  ArrayList<Movies>()
            arrSubCategory.add(Movies(1,"title","https://m.media-amazon.com/images/M/MV5BM2Q0ZGE2N2EtZGI5Zi00MTlhLTg1NDktY2M2ZmY4Zjk2ZmViXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_UX128_CR0,12,128,176_AL_.jpg"))
            subCategoryAdapter.setData(arrSubCategory)
            binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            binding.rvSubCategory.adapter = subCategoryAdapter
    }


    fun initializeCategories(context : Context){
        val inputStream = requireContext().resources.openRawResource(R.raw.categories)
        val categories = Klaxon().parse<CategoryItem>(inputStream)
        for(maincategories in categories?.maincategories!!){
            var resourceId = context.getResources().getIdentifier(maincategories.icon, "drawable", context.getPackageName()).toString();
            arrMainCategory.add(Movies(maincategories.m_id.toInt(),maincategories.title,resourceId))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun testAPISub(){
        val inputStream = requireContext().resources.openRawResource(R.raw.commingup)
        val movies = Klaxon().parse<MovieItem>(inputStream)

        for(movie in movies?.items!!){
            var id : Int = movie.id.subSequence(2,movie.id.length).toString().toInt()
            arrSubCategory.add(Movies(id,movie.title,movie.image))
        }
    }

//    override fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }
}