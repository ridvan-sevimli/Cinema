package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ch.mov.cinema.cinemaapp.model.TriviaDataViewModel
import ch.mov.cinema.cinemaapp.model.entities.Answers
import ch.mov.cinema.cinemaapp.model.entities.Questions
import ch.mov.cinema.databinding.QuestionViewBinding
import ch.mov.cinema.enums.TriviaKeyIds
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuestionFragment : Fragment() {

    private var _binding: QuestionViewBinding? = null


    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QuestionViewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: TriviaDataViewModel by activityViewModels()
        val questions: MutableMap<Int, Questions> = mutableMapOf<Int, Questions>()
        val answers: MutableMap<Int, Answers> = mutableMapOf<Int, Answers>()
        val settings = context?.getSharedPreferences("prefsfile", Context.MODE_PRIVATE)
        var questionId = settings?.getString(TriviaKeyIds.QUESTION_ID.triviaKey, "00000")
        var answer : String? = ""

       lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                var answerArray = model.getAnswers()!!
                var question = model.getMixed()!!
                for(questi in question){
                    questions[questi.id] = questi
                }
                for(answer in answerArray){
                    answers[answer.id] = answer
                }

                binding.question.text = questions.get(questionId?.toInt())?.questions
                binding.btnAnswerA.text =answers?.get(questionId?.toInt())?.a
                binding.btnAnswerB.text =answers?.get(questionId?.toInt())?.b
                binding.btnAnswerC.text =answers?.get(questionId?.toInt())?.c
                binding.btnAnswerD.text =answers?.get(questionId?.toInt())?.d
                answer =  answers?.get(questionId?.toInt())?.answers

            }
           Picasso.get()
               .load(questions.get(questionId?.toInt())?.poster)
               .into(binding.poster)

           binding.btnAnswerA.setOnClickListener{
               if(checkAnswer(answer, binding.btnAnswerA.text.toString())){
                   println("True")
               }else{
                   println("False")
               }
           }

           binding.btnAnswerB.setOnClickListener{
               if(checkAnswer(answer, binding.btnAnswerB.text.toString())){
                   println("True")
               }else{
               println("False")
           }
           }

           binding.btnAnswerC.setOnClickListener{
               if(checkAnswer(answer, binding.btnAnswerC.text.toString())){
                   println("True")
               }else{
               println("False")
           }
           }

           binding.btnAnswerD.setOnClickListener{
              if( checkAnswer(answer, binding.btnAnswerD.text.toString())){
                   println("True")
               }else{
               println("False")
           }
           }

        }


        binding.btnNext.setOnClickListener{
            val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
            val editor = setting?.edit()
            editor?.putString(TriviaKeyIds.QUESTION_ID.triviaKey,next(questionId?.toInt()!!,questions).toString())
            editor?.commit()
            findNavController().navigate(R.id.action_QuestionFragment_to_QuestionFragment)
        }


//        lifecycleScope.launchWhenStarted {
//            withContext(Dispatchers.Default) {
//                val requestQueue = Volley.newRequestQueue(requireContext())
//                val request = StringRequest(
//                    Request.Method.GET,
//                    "https://api.themoviedb.org/3/movie/tt$movieId?api_key=75fb8838189fad79cf4dab173ae0245a&language=en-US",
//                    Response.Listener<String> { response ->
//                        var string = response
//                        var overview = Klaxon().parse<Overview>(string)
//                        var run_time = Klaxon().parse<RunTime>(string)
//                        var realeaseDate = Klaxon().parse<ReleaseDate>(string)
//                        var title = Klaxon().parse<Title>(string)
//                        var rating = Klaxon().parse<Rating>(string)
//                        var poster = Klaxon().parse<Poster>(string)
//                        var tagline = Klaxon().parse<Tagline>(string)
//
//                        binding.fullTitle.text = title?.title
//                        binding.releaseDate.text = realeaseDate?.release_date
//                        binding.runTime.text = "${run_time?.runtime} min"
//                        binding.rating.text = rating?.vote_average?.toString()
//                        binding.tagLine.text = tagline?.tagline
//                        binding.information.text = overview?.overview
//
//
//                        Picasso.get()
//                            .load("https://image.tmdb.org/t/p/original/${poster?.poster_path}")
//                            .into(binding.poster)
//
//
//                    },
//                    Response.ErrorListener {
//
//                    })
//                requestQueue.add(request)
//            }
//        }

    }

    fun checkAnswer(answer: String?, ckeckString : String?) : Boolean{
        if(answer == ckeckString){
            return true
        }
        return false
    }
    fun next(questionId : Int, questions: MutableMap<Int, Questions>) : Int{
        if(questionId <= questions.size){
            return questionId + 1
        }else{
            return 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

