package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ch.mov.cinema.cinemaapp.model.TriviaDataViewModel
import ch.mov.cinema.cinemaapp.model.dataClass.AnswerItem
import ch.mov.cinema.cinemaapp.model.dataClass.QuestionItem
import ch.mov.cinema.cinemaapp.model.entities.*
import ch.mov.cinema.databinding.SplaschScreenBinding
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SplashFragment : Fragment() {

    private var _binding: SplaschScreenBinding? = null
    val model: TriviaDataViewModel by activityViewModels()
    private val ALLOWED_PLAYER = 2
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplaschScreenBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var players : ArrayList<Players> = arrayListOf()
        model.initDB(requireContext())

        fillQuestions_to_DB()
        fillAnswers_to_DB()


        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                 players = model.getPlayers() as ArrayList<Players>
            }
            if(players.size == ALLOWED_PLAYER){
                findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)
            }
        }

        binding.btnSavePlayer.setOnClickListener {
            var playerName = binding.playerNameEdit.text.toString()
            if(playerName != "" && players.size < ALLOWED_PLAYER){
                hideKeyboard()
                var playerSize = players.size
                players.add(Players(playerSize.plus(1),playerName,0))
                lifecycleScope.launchWhenStarted {
                    withContext(Dispatchers.Default) {
                        model.insertPlayer(players)
                    }
                    if(players.size == 2){
                        findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)

                    }else{
                        findNavController().navigate(R.id.action_SplashFragment_self)
                    }
                }
            }else{
                findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)
            }
        }
    }

   fun fillQuestions_to_DB(){
       var arrSubCategory = ArrayList<Questions>()
       val inputStream = requireContext().resources.openRawResource(R.raw.questions)
       val questions = Klaxon().parse<QuestionItem>(inputStream)
       for (question in questions?.questions!!) {
           arrSubCategory.add(
               Questions(
                   question.q_id,
                   question.category,
                   question.question,
                   question.poster,
                   question.isAnswered,
                   question.answer
               )
           )
       }
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                model.insertQuestions(arrSubCategory)
                }
            }
        }

    fun fillAnswers_to_DB(){
        var arrSubCategory = ArrayList<Answers>()
        val inputStream = requireContext().resources.openRawResource(R.raw.answers)
        val answers = Klaxon().parse<AnswerItem>(inputStream)
        for (answer in answers?.answers!!) {
            arrSubCategory.add(
                Answers(
                    answer.q_id,
                    answer.type,
                    answer.a,
                    answer.b,
                    answer.c,
                    answer.d,
                    answer.answer
                )
            )
        }
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                model.insertAnswers(arrSubCategory)
            }
        }
    }

    //by default, the user can show or hide the keyboard
//using the bottom left arrow button. If the keyboard
//should be automatically hidden, use the following method
    fun hideKeyboard() {
        val manager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (manager != null) {
            manager.hideSoftInputFromWindow(
                requireActivity()
                    .findViewById<View>(android.R.id.content).windowToken, 0)
        }
        binding.playerNameEdit.clearFocus() //remove focus from EditText
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

