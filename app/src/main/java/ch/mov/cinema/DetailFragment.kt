package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ch.mov.cinema.cinemaapp.model.MovieDataViewModel
import ch.mov.cinema.databinding.DetailViewBinding
import com.beust.klaxon.Klaxon
import com.squareup.picasso.Picasso


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
        var imagePath = settings?.getString("IMAGE_PATH","234")


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

