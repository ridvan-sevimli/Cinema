package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import ch.mov.cinema.cinemaapp.model.MovieDataViewModel
import ch.mov.cinema.cinemaapp.model.entities.DetailItem
import ch.mov.cinema.databinding.DetailViewBinding
import ch.mov.cinema.enums.MovieKeyIds
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


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
        var imageId = settings?.getString(MovieKeyIds.MOVIE_ID.movieKey, "234")

        var movies: String? = null

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                val requestQueue = Volley.newRequestQueue(requireContext())
                val request = StringRequest(
                    Request.Method.GET,
                    "https://api.themoviedb.org/3/movie/0111161?api_key=75fb8838189fad79cf4dab173ae0245a&language=en-US",
                    Response.Listener<String> { response ->
                        binding.information.text =  response
                    },
                    Response.ErrorListener {

                    })
                requestQueue.add(request)
            }
        }



//        while(!job.isCompleted){
//
//        }


        var ms = movies

                Picasso.get()
                    .load(imagePath).into(binding.poster)


//        binding.btnGetStarted.setOnClickListener {
//            findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)
//        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

