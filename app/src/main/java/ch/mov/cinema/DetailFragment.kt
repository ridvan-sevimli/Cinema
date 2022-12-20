package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import ch.mov.cinema.cinemaapp.model.DetailViewEntities.*
import ch.mov.cinema.cinemaapp.model.MovieDataViewModel
import ch.mov.cinema.databinding.DetailViewBinding
import ch.mov.cinema.enums.MovieKeyIds
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: DetailViewBinding? = null
    val model: MovieDataViewModel by activityViewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailViewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val settings = context?.getSharedPreferences("prefsfile", Context.MODE_PRIVATE)
        var imagePath = settings?.getString(MovieKeyIds.IMAGE_PATH.movieKey, "234")
        var movieId = settings?.getString(MovieKeyIds.MOVIE_ID.movieKey, "234")


        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                val requestQueue = Volley.newRequestQueue(requireContext())
                val request = StringRequest(
                    Request.Method.GET,
                    "https://api.themoviedb.org/3/movie/tt$movieId?api_key=75fb8838189fad79cf4dab173ae0245a&language=en-US",
                    Response.Listener<String> { response ->
                        var string = response
                        var overview = Klaxon().parse<Overview>(string)
                        var run_time = Klaxon().parse<RunTime>(string)
                        var realeaseDate = Klaxon().parse<ReleaseDate>(string)
                        var title = Klaxon().parse<Title>(string)
                        var rating = Klaxon().parse<Rating>(string)
                        var poster = Klaxon().parse<Poster>(string)

                        binding.fullTitle.text = title?.title
                        binding.releaseDate.text = realeaseDate?.release_date
                        binding.runTime.text = "${run_time?.runtime} min"
                        binding.rating.text = rating?.vote_average?.toString()
                        binding.information.text = overview?.overview

                        Picasso.get().load("https://image.tmdb.org/t/p/original/${poster?.poster_path}").into(binding.poster)


                    },
                    Response.ErrorListener {

                    })
                requestQueue.add(request)
            }
        }







//        binding.btnGetStarted.setOnClickListener {
//            findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)
//        }
    }

    fun getRequest(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

