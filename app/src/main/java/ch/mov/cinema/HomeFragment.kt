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
import androidx.navigation.fragment.findNavController
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

        initializeCategories(requireContext())


        mainCategoryAdapter.setData(arrMainCategory)
        mainCategoryAdapter.setClickListener(onClickedMainCateogry)
        binding.rvMainCategory.adapter = mainCategoryAdapter
        binding.rvMainCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)


        subCategoryAdapter.setData(arrSubCategory)
        subCategoryAdapter.setClickListener(onCLickedSubCategory)
        binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


        lifecycleScope.launchWhenStarted{
            withContext(Dispatchers.IO){
                var movies = model.getComingSoon()
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


        /**
         *  my implementation
         */

    }
    private val onClickedMainCateogry  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            binding.textViewCategory.text = categoryName
            //changeMovies(categoryName)
            arrSubCategory = ArrayList<Movie>()
            lifecycleScope.launchWhenStarted{
                withContext(Dispatchers.Default){
                    var movies : MutableList<Movie>? = null
                    if(categoryName == "Coming Soon"){
                        movies = model.getComingSoon()!!
                    } else if (categoryName == "Top 250 Movies") {
                        movies = model.getTop250movies()!!
                    }else if(categoryName == "Most Popular Movies"){
                        movies = model.getMostPopularMovies()!!
                    }else if(categoryName == "Most Popular Tv's"){
                        movies = model.getMostPopularTv()!!
                    }else if(categoryName == "In Theaters"){
                        movies = model.getInTheaters()!!
                    }
                    if (movies != null) {
                        for(movie in movies){
                            arrSubCategory.add(movie)
                        }
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
        }
    }

    private val onCLickedSubCategory  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
            val editor = setting?.edit()
            editor?.putString("MOVIE_ID",id)
            editor?.commit()
            findNavController().navigate(R.id.action_HomeFragment_to_DetailFragment)
        }
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
            arrMainCategory.add(Movie(maincategories.m_id.toInt(),"coming_soon",maincategories.title,resourceId))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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