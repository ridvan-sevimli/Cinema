package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ch.mov.cinema.cinemaapp.model.MovieDataViewModel
import ch.mov.cinema.cinemaapp.model.adapter.MainCategoryAdapter
import ch.mov.cinema.cinemaapp.model.adapter.SubCategoryAdapter
import ch.mov.cinema.cinemaapp.model.database.MoviesDatabase
import ch.mov.cinema.cinemaapp.model.entities.*
import ch.mov.cinema.databinding.FragmentHomeBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    val model: MovieDataViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var subCategoryAdapter = SubCategoryAdapter()
    var mainCategoryAdapter = MainCategoryAdapter()
    var arrMainCategory = ArrayList<Movie>()
    var arrSubCategory = ArrayList<Movie>()


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


        model.initDB(requireContext())




        /**
         * Test API
         */

        //testAPISub()


        initializeCategories(requireContext())


        mainCategoryAdapter.setData(arrMainCategory)
        mainCategoryAdapter.setClickListener(onCLicked)
        binding.rvMainCategory.adapter = mainCategoryAdapter
        binding.rvMainCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)


        subCategoryAdapter.setData(arrSubCategory)
        binding.rvSubCategory.adapter = subCategoryAdapter
        binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


        lifecycleScope.launchWhenStarted{
            withContext(Dispatchers.Default){
                var movies = model.readMovies()
                for(movie in movies!!){
                    arrSubCategory.add(movie)
                }
            }
        }

        model.movies.observe(viewLifecycleOwner,
            // note that the observer sends the new value as parameter
            Observer<MutableList<Movie>>{newVal ->
                subCategoryAdapter.setData(arrSubCategory)
                binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                binding.rvSubCategory.adapter = subCategoryAdapter
            })


//        lifecycleScope.launchWhenStarted{
//            withContext(Dispatchers.Default){
//                model.insertMovies()
//            }
//        }

        /**
         *  my implementation
         */

    }
    private val onCLicked  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            binding.textViewCategory.text = categoryName
            //changeMovies(categoryName)
            arrSubCategory = ArrayList<Movie>()
            lifecycleScope.launchWhenStarted{
                withContext(Dispatchers.Default){
                    var something = model.readMovies()
                    arrSubCategory.add(something!![0])

                }
            }
            model.movies.observe(viewLifecycleOwner,
                // note that the observer sends the new value as parameter
                Observer<MutableList<Movie>>{newVal ->
                    subCategoryAdapter.setData(arrSubCategory)
                    binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    binding.rvSubCategory.adapter = subCategoryAdapter
                })
        }
    }

    private fun changeMovies(categoryName:String){
        val inputStream = requireContext().resources.openRawResource(getJsonMovies(categoryName))
        val movies = Klaxon().parse<MovieItem>(inputStream)
        arrSubCategory =  ArrayList<Movie>()
        for(movie in movies?.items!!){
            var id : Int = movie.id.subSequence(2,movie.id.length).toString().toInt()
            arrSubCategory.add(Movie(id,movie.title,movie.image))
        }
            var subCategoryAdapter = SubCategoryAdapter()
            subCategoryAdapter.setData(arrSubCategory)
            binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            binding.rvSubCategory.adapter = subCategoryAdapter
    }

    fun getJsonMovies(categoryName: String) : Int{
        return when(categoryName){
            "Coming Soon" -> R.raw.comingsoon
            "Top 250 Movies" -> R.raw.top_250_movies
            "Most Popular Movies" -> R.raw.most_popular_movies
            "Most Popular Tv's" -> R.raw.most_popular_tv
            "In Theaters" -> R.raw.in_theaters
            "Box Office" -> R.raw.box_office
            "Box Office All Time" -> R.raw.box_office_all_time
            else -> {
                R.raw.comingsoon
            }
        }
    }

    fun initializeCategories(context : Context){
        val inputStream = requireContext().resources.openRawResource(R.raw.categories)
        val categories = Klaxon().parse<CategoryItem>(inputStream)
        for(maincategories in categories?.maincategories!!){
            var resourceId = context.getResources().getIdentifier(maincategories.icon, "drawable", context.getPackageName()).toString();
            arrMainCategory.add(Movie(maincategories.m_id.toInt(),maincategories.title,resourceId))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun testAPISub(){
        val inputStream = requireContext().resources.openRawResource(R.raw.comingsoon)
        val movies = Klaxon().parse<MovieItem>(inputStream)

        for(movie in movies?.items!!){
            var id : Int = movie.id.subSequence(2,movie.id.length).toString().toInt()
            arrSubCategory.add(Movie(id,movie.title,movie.image))
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