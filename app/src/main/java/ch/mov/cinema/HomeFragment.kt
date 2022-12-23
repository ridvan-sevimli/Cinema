package ch.mov.cinema

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ch.mov.cinema.cinemaapp.model.TriviaDataViewModel
import ch.mov.cinema.cinemaapp.model.adapter.MainCategoryAdapter
import ch.mov.cinema.cinemaapp.model.adapter.SubCategoryAdapter
import ch.mov.cinema.cinemaapp.model.entities.*
import ch.mov.cinema.databinding.FragmentHomeBinding
import ch.mov.cinema.enums.TriviaKeyIds
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    val model: TriviaDataViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    var subCategoryAdapter = SubCategoryAdapter()
    var mainCategoryAdapter = MainCategoryAdapter()
    var arrMainCategory = ArrayList<Category>()
    var arrSubCategory = ArrayList<Questions>()
    var players = ArrayList<Players>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.initDB(requireContext())

        initializeCategories(requireContext())

        mainCategoryAdapter.setData(arrMainCategory)
        mainCategoryAdapter.setClickListener(onClickedMainCateogry)
        binding.rvMainCategory.adapter = mainCategoryAdapter
        binding.rvMainCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        if(getCurrentPlayer() == 2){
            fillQuestions_to_DB()
            getSelectedCategory()
            lifecycleScope.launchWhenStarted {
                withContext(Dispatchers.Default) {
                    arrSubCategory = model.getMixed() as ArrayList<Questions>
                    players = model.getPlayers() as ArrayList<Players>

                }
                subCategoryAdapter.setData(arrSubCategory)
                subCategoryAdapter.setClickListener(onCLickedSubCategory)
                binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                for(player in players){
                    if(player.id == getCurrentPlayer()){
                        binding.currentPlayer.text = player.Name
                    }
                }

            }
        }else{
            lifecycleScope.launchWhenStarted {
                withContext(Dispatchers.Default) {
                    arrSubCategory = model.getMixed() as ArrayList<Questions>
                    players = model.getPlayers() as ArrayList<Players>
                    saveCurrentPlayer(players[players.size-1].id)

                }
                subCategoryAdapter.setData(arrSubCategory)
                subCategoryAdapter.setClickListener(onCLickedSubCategory)
                binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

                binding.currentPlayer.text = players[getCurrentPlayer()!!].Name
            }
        }


        model.questions.observe(viewLifecycleOwner,
            // note that the observer sends the new value as parameter
            Observer<MutableList<Questions>>{newVal ->
                subCategoryAdapter.setData(arrSubCategory)
                binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                binding.rvSubCategory.adapter = subCategoryAdapter


            })
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



    private val onClickedMainCateogry  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            binding.textViewCategory.text = categoryName
            saveSelectedCategory(categoryName)
            arrSubCategory = ArrayList<Questions>()
            lifecycleScope.launchWhenStarted{
                withContext(Dispatchers.Default){
                    var questions : MutableList<Questions>? = null
                    if(categoryName == "Mixed"){
                        questions = model.getMixed()!!
                    } else if (categoryName == "Top 250 Movies") {
                        //movies = model.getTop250movies()!!
                    }else if(categoryName == "Most Popular Movies"){
                        //movies = model.getMostPopularMovies()!!
                    }else if(categoryName == "Most Popular Tv's"){
                        //movies = model.getMostPopularTv()!!
                    }else if(categoryName == "In Theaters"){
                        //movies = model.getInTheaters()!!
                    }
                    if (questions != null) {
                        for(question in questions){
                            arrSubCategory.add(question)
                        }
                    }
                }
            }
            model.questions.observe(viewLifecycleOwner,
                // note that the observer sends the new value as parameter
                Observer<MutableList<Questions>>{newVal ->
                    subCategoryAdapter.setData(arrSubCategory)
                    binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    binding.rvSubCategory.adapter = subCategoryAdapter
                })
        }
    }

    private val onCLickedSubCategory  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(imagePath: String, questionId: String, CategoryId : String) {
            val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
            val editor = setting?.edit()
            editor?.putString(TriviaKeyIds.POSTER_PATH.triviaKey,imagePath)
            editor?.putString(TriviaKeyIds.QUESTION_ID.triviaKey,questionId)
            editor?.putString(TriviaKeyIds.CATEGORY_ID.triviaKey,CategoryId)
            editor?.commit()
            findNavController().navigate(R.id.action_HomeFragment_to_DetailFragment)
        }
    }

    suspend fun saveCurrentPlayer(palyerId: Int){
        val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
        val editor = setting?.edit()
        editor?.putInt(TriviaKeyIds.CURRENT_PLAYER_ID.triviaKey,palyerId)
        editor?.commit()
    }


    fun saveSelectedCategory(categoryId: String){
        val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
        val editor = setting?.edit()
        editor?.putString(TriviaKeyIds.SELECTED_CATEGORY.triviaKey,categoryId)
        editor?.commit()
    }

    fun getSelectedCategory() : String?{
        val settings = context?.getSharedPreferences("prefsfile", Context.MODE_PRIVATE)
        var selectedCategoryId = settings?.getString(TriviaKeyIds.SELECTED_CATEGORY.triviaKey, "Mixed")
        return selectedCategoryId
    }

    fun getCurrentPlayer() : Int?{
        val settings = context?.getSharedPreferences("prefsfile", Context.MODE_PRIVATE)
        var currentPlayerId = settings?.getInt(TriviaKeyIds.CURRENT_PLAYER_ID.triviaKey, 0)
        return (currentPlayerId)
    }

    fun initializeCategories(context : Context){
        if(arrMainCategory.size == 0) {
            val inputStream = requireContext().resources.openRawResource(R.raw.categories)
            val categories = Klaxon().parse<CategoryItem>(inputStream)
            for (maincategories in categories?.maincategories!!) {
                var resourceId = context.getResources()
                    .getIdentifier(maincategories.icon, "drawable", context.getPackageName())
                    .toString();
                arrMainCategory.add(
                    Category(
                        maincategories.m_id,
                        maincategories.title,
                        resourceId
                    )
                )
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