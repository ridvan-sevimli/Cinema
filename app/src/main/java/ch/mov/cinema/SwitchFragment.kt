package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ch.mov.cinema.cinemaapp.model.TriviaDataViewModel
import ch.mov.cinema.cinemaapp.model.entities.*
import ch.mov.cinema.databinding.TimeToSwitchBinding
import ch.mov.cinema.enums.TriviaKeyIds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SwitchFragment : Fragment() {

    private var _binding: TimeToSwitchBinding? = null
    val model: TriviaDataViewModel by activityViewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var arrSubCategory = ArrayList<Questions>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TimeToSwitchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.initDB(requireContext())


        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                var players = model.getPlayers()
                val settings = context?.getSharedPreferences("prefsfile", Context.MODE_PRIVATE)
                val editor = settings?.edit()
                var points = settings?.getInt(TriviaKeyIds.CURRENT_PLAYER_POINT.triviaKey, 0)
                var playerId = settings?.getInt(TriviaKeyIds.CURRENT_PLAYER_ID.triviaKey, 0)
                model.updatePlayers(Players(playerId!!, players?.get(playerId)?.Name,points!!))
                model.clearQuestionsDb()
                editor?.putInt(TriviaKeyIds.CURRENT_PLAYER_ID.triviaKey, playerId + 1)
                editor?.commit()
                resetPoints()
            }
        }

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_SwitchFragment_to_HomeFragment)
        }
    }

    fun resetPoints(){
        val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
        val editor = setting?.edit()
        editor?.putInt(TriviaKeyIds.CURRENT_PLAYER_POINT.triviaKey,0)
        editor?.commit()
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

