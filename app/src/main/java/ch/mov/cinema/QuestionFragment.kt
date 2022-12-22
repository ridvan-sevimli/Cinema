package ch.mov.cinema

import android.content.Context
import android.graphics.Color
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
import ch.mov.cinema.cinemaapp.model.entities.Players
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
    var player : Players? = null


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
        var categoryId = settings?.getString(TriviaKeyIds.CATEGORY_ID.triviaKey, "00000")
        var playerId = settings?.getInt(TriviaKeyIds.CURRENT_PLAYER_ID.triviaKey, 0)

        var answer : String? = ""

       lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                var players = model.getPlayers()
                for(i_player in players!!){
                    if(i_player.id == playerId){
                        player = i_player
                    }
                }
                var answerArray = model.getAnswers()!!
                if(categoryId == "mixed"){
                    var question = model.getMixed()!!
                    for(questi in question){
                        questions[questi.id] = questi
                    }
                }

                for(answer in answerArray){
                    answers[answer.id] = answer
                }

                if(questions.get(questionId?.toInt())?.isAnswered!!){
                    binding.question.text = questions.get(questionId?.toInt())?.questions
                    binding.answerA.text =answers?.get(questionId?.toInt())?.a
                    binding.answerB.text =answers?.get(questionId?.toInt())?.b
                    binding.answerC.text =answers?.get(questionId?.toInt())?.c
                    binding.answerD.text =answers?.get(questionId?.toInt())?.d
                    answer =  answers?.get(questionId?.toInt())?.answers
                    setGreen(questions.get(questionId?.toInt())?.answer)
                }else{
                    binding.question.text = questions.get(questionId?.toInt())?.questions
                    binding.answerA.text =answers?.get(questionId?.toInt())?.a
                    binding.answerB.text =answers?.get(questionId?.toInt())?.b
                    binding.answerC.text =answers?.get(questionId?.toInt())?.c
                    binding.answerD.text =answers?.get(questionId?.toInt())?.d
                    answer =  answers?.get(questionId?.toInt())?.answers
                }
            }
           Picasso.get()
               .load(questions.get(questionId?.toInt())?.poster)
               .into(binding.poster)

           if(questions.get(questionId?.toInt())?.isAnswered!!){
               enableAnswerButtons(false)
               enableNextButton(true)
           }
        }

        enableNextButton(false)

        binding.btnAnswerA.setOnClickListener{
            setGreen(answer)
            validatePoint(answer,binding.answerA.text.toString())
            enableNextButton(true)
            lockQuestion(questionId,categoryId,answer,questions,model)
        }

        binding.btnAnswerB.setOnClickListener{
            setGreen(answer)
            validatePoint(answer,binding.answerB.text.toString())
            enableNextButton(true)
            lockQuestion(questionId,categoryId,answer,questions,model)
        }

        binding.btnAnswerC.setOnClickListener{
            setGreen(answer)
            validatePoint(answer,binding.answerC.text.toString())
            enableNextButton(true)
            lockQuestion(questionId,categoryId,answer,questions,model)
        }

        binding.btnAnswerD.setOnClickListener{
            setGreen(answer)
            validatePoint(answer,binding.answerD.text.toString())
            enableNextButton(true)
            lockQuestion(questionId,categoryId,answer,questions,model)
        }


        binding.btnNext.setOnClickListener{
            val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
            val editor = setting?.edit()
            var nextquestion = next(questionId?.toInt()!!,questions)
            if(nextquestion >= 0){
                editor?.putString(TriviaKeyIds.QUESTION_ID.triviaKey,nextquestion.toString())
                editor?.commit()
                findNavController().navigate(R.id.action_QuestionFragment_to_QuestionFragment)
            }else{
                findNavController().navigate(R.id.action_QuestionFragment_to_switchFragment)
            }
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

    fun savePoint(point: Int){
        val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
        val editor = setting?.edit()
        var currentPoint = setting?.getInt(TriviaKeyIds.CURRENT_PLAYER_POINT.triviaKey, 0)
        currentPoint = currentPoint?.plus(point)
        if(point != 0){
            editor?.putInt(TriviaKeyIds.CURRENT_PLAYER_POINT.triviaKey,currentPoint!!)
            editor?.commit()
            println(currentPoint)
        }
    }

    fun lockQuestion(questionId: String?, categoryId: String?,answer : String?, questions: MutableMap<Int, Questions>, model: TriviaDataViewModel){
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                var id = questionId?.toInt()!!
                var category = categoryId
                var question = questions.get(questionId?.toInt())?.questions
                var poster = questions.get(questionId?.toInt())?.poster
                model.updateQuestion(Questions(id,category,question,poster,true,answer))
            }
        }
    }

    fun setGreen(answer: String?){
        setRed()
        when(answer){
            binding.answerA.text.toString() -> binding.btnAnswerA.setCardBackgroundColor(Color.parseColor("#76E10A"))
            binding.answerB.text.toString() -> binding.btnAnswerB.setCardBackgroundColor(Color.parseColor("#76E10A"))
            binding.answerC.text.toString() -> binding.btnAnswerC.setCardBackgroundColor(Color.parseColor("#76E10A"))
            binding.answerD.text.toString() -> binding.btnAnswerD.setCardBackgroundColor(Color.parseColor("#76E10A"))
        }
    }

    fun validatePoint(correctAnswer: String?,givenAnwer: String ){
        if(correctAnswer == givenAnwer ) {
            savePoint(1)
        }
    }


   suspend fun enableAnswerButtons(enabled: Boolean){
        binding.btnAnswerA.isEnabled = enabled
        binding.btnAnswerB.isEnabled = enabled
        binding.btnAnswerC.isEnabled = enabled
        binding.btnAnswerD.isEnabled = enabled
        binding.btnNext.isEnabled=enabled
    }

   fun enableNextButton(enabled: Boolean){
        binding.btnNext.isEnabled = enabled
        if(enabled){
            binding.btnNext.setCardBackgroundColor(Color.parseColor("#F5C517"))
        }
    }

    fun setRed(){
        binding.btnAnswerA.setCardBackgroundColor(Color.parseColor("#EC0A2C"))
        binding.btnAnswerB.setCardBackgroundColor(Color.parseColor("#EC0A2C"))
        binding.btnAnswerC.setCardBackgroundColor(Color.parseColor("#EC0A2C"))
        binding.btnAnswerD.setCardBackgroundColor(Color.parseColor("#EC0A2C"))
    }
    fun next(questionId : Int, questions: MutableMap<Int, Questions>) : Int{
        if(questionId < questions.size){
            return questionId + 1
        }else{
           return -1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

