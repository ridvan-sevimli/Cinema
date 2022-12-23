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
import ch.mov.cinema.databinding.FragmentWinningBinding
import ch.mov.cinema.enums.TriviaKeyIds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WinFragment : Fragment() {

    private var _binding: FragmentWinningBinding? = null
    val model: TriviaDataViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWinningBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetSelectedCategory()
        resetCurrentPlayer()
        resetPoints()
        model.initDB(requireContext())
        var maxPoint = 0
        var winner : String? = ""

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                var players = model.getPlayers()
                for(player in players!!){
                    if(player.points > 0){
                        maxPoint = player.points
                        winner = player.Name
                    }
                }
                model.clearPlayer()
                model.clearQuestionsDb()
            }
            binding.points.text = maxPoint.toString()
            binding.winner.text = winner
        }


        binding.btnGoAgain.setOnClickListener {
            findNavController().navigate(R.id.action_WinFragment_to_SplashFragment)
        }
    }

    fun resetSelectedCategory(){
        val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
        val editor = setting?.edit()
        editor?.putString(TriviaKeyIds.SELECTED_CATEGORY.triviaKey,"Mixed")
        editor?.commit()
    }

    fun resetCurrentPlayer(){
        val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
        val editor = setting?.edit()
        editor?.putInt(TriviaKeyIds.CURRENT_PLAYER_ID.triviaKey,0)
        editor?.commit()
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

