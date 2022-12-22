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
import ch.mov.cinema.cinemaapp.model.CategoryHandler
import ch.mov.cinema.cinemaapp.model.TriviaDataViewModel
import ch.mov.cinema.cinemaapp.model.entities.*
import ch.mov.cinema.databinding.TimeToSwitchBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SwitchFragment : Fragment() {

    private var _binding: TimeToSwitchBinding? = null
    val model: TriviaDataViewModel by activityViewModels()
    var categoryHandler = CategoryHandler()
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

//        fillQuestions_to_DB()
//        fillAnswers_to_DB()

//        lifecycleScope.launchWhenStarted {
//            withContext(Dispatchers.Default) {
//                var players = ArrayList<Players>()
//                players.add(Players(0,"Player1",0))
//                players.add(Players(1,"Player2",0))
//                model.insertPlayer(players)
//            }
//        }

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_SplashFragment_to_HomeFragment)
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

