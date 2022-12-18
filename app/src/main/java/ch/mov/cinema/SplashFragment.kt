package ch.mov.cinema

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ch.mov.cinema.cinemaapp.model.MovieDataViewModel
import ch.mov.cinema.cinemaapp.model.adapter.MainCategoryAdapter
import ch.mov.cinema.cinemaapp.model.adapter.SubCategoryAdapter
import ch.mov.cinema.cinemaapp.model.entities.Movie
import ch.mov.cinema.cinemaapp.model.entities.MovieItem
import ch.mov.cinema.databinding.SplaschScreenBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SplashFragment : Fragment() {

    private var _binding: SplaschScreenBinding? = null
    val model: MovieDataViewModel by activityViewModels()
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
        _binding = SplaschScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.initDB(requireContext())


        lifecycleScope.launchWhenStarted{
            withContext(Dispatchers.Default){
                fillDataBase()
            }
        }

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)
        }
    }

    fun fillDataBase(){
        var keymap = mapOf("https://imdb-api.com/en/API/Top250Movies/k_c5ew4idg" to "top_250_movies",
            "https://imdb-api.com/en/API/ComingSoon/k_c5ew4idg" to "coming_soon"
            )

        for(key in keymap){
            val requestQueue = Volley.newRequestQueue(requireContext())
            val request = StringRequest(
                Request.Method.GET, key.key, { response ->
                    var movies = Klaxon().parse<MovieItem>(response)
                    for(movie in movies?.items!!){
                        var id : Int = movie.id.subSequence(2,movie.id.length).toString().toInt()
                        arrSubCategory.add(Movie(id,key.value,movie.title,movie.image))
                    }
                    lifecycleScope.launchWhenStarted{
                        withContext(Dispatchers.Default){
                            model.insertMovies(arrSubCategory)
                        }
                    }
                },
                {
                    TODO("Error handling")
                })

            requestQueue.add(request)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}

