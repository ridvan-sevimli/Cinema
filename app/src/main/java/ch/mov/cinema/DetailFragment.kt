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
import ch.mov.cinema.cinemaapp.model.entities.CategoryItem
import ch.mov.cinema.cinemaapp.model.entities.Movie
import ch.mov.cinema.cinemaapp.model.entities.MovieItem
import ch.mov.cinema.cinemaapp.model.entities.PosterItem
import ch.mov.cinema.databinding.DetailViewBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
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
        var movieId = settings?.getString("MOVIE_ID","234")


        val inputStream = requireContext().resources.openRawResource(R.raw.poster)
        val poster = Klaxon().parse<PosterItem>(inputStream)

//        val requestQueue = Volley.newRequestQueue(requireContext())
//        val request = StringRequest(
//            Request.Method.GET,, { response ->
//
//
//
//            },
//            {
//                TODO("Error handling")
//            })


        Picasso.get()
            .load(poster?.posters!!.get(15).link).into(binding.poster)


//        binding.btnGetStarted.setOnClickListener {
//            findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

