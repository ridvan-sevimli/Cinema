package ch.mov.cinema

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import ch.mov.cinema.enums.MovieKeyIds
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



        val inputStream = requireContext().resources.openRawResource(R.raw.questions)
        val questions = Klaxon().parse<QuestionItem>(inputStream)
        for (question in questions?.questions!!) {
            arrSubCategory.add(
                Questions(
                    question.q_id,
                    question.category,
                    question.question,
                    question.poster
                )
            )
        }


        mainCategoryAdapter.setData(arrMainCategory)
        mainCategoryAdapter.setClickListener(onClickedMainCateogry)
        binding.rvMainCategory.adapter = mainCategoryAdapter
        binding.rvMainCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)


        subCategoryAdapter.setData(arrSubCategory)
        subCategoryAdapter.setClickListener(onCLickedSubCategory)
        binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


//        lifecycleScope.launchWhenStarted{
//            withContext(Dispatchers.Default){
//                var movies = model.getComingSoon()
//                for(movie in movies!!){
//                    arrSubCategory.add(movie)
//                }
//            }
//        }

        model.movies.observe(viewLifecycleOwner,
            // note that the observer sends the new value as parameter
            Observer<MutableList<Questions>>{newVal ->
                subCategoryAdapter.setData(arrSubCategory)
                binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                binding.rvSubCategory.adapter = subCategoryAdapter
            })
    }


    private val onClickedMainCateogry  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            binding.textViewCategory.text = categoryName
            arrSubCategory = ArrayList<Questions>()
            lifecycleScope.launchWhenStarted{
                withContext(Dispatchers.Default){
                    var movies : MutableList<Questions>? = null
                    if(categoryName == "Coming Soon"){
                        movies = model.getComingSoon()!!
                    } else if (categoryName == "Top 250 Movies") {
                        movies = model.getTop250movies()!!
                    }else if(categoryName == "Most Popular Movies"){
                        movies = model.getMostPopularMovies()!!
                    }else if(categoryName == "Most Popular Tv's"){
                        movies = model.getMostPopularTv()!!
                    }else if(categoryName == "In Theaters"){
                        movies = model.getInTheaters()!!
                    }
                    if (movies != null) {
                        for(movie in movies){
                            arrSubCategory.add(movie)
                        }
                    }
                }
            }
            model.movies.observe(viewLifecycleOwner,
                // note that the observer sends the new value as parameter
                Observer<MutableList<Questions>>{newVal ->
                    subCategoryAdapter.setData(arrSubCategory)
                    binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    binding.rvSubCategory.adapter = subCategoryAdapter
                })
        }
    }

    private val onCLickedSubCategory  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(imagePath: String, movieId: String) {
            val setting = context?.getSharedPreferences("prefsfile",Context.MODE_PRIVATE)
            val editor = setting?.edit()
            editor?.putString(MovieKeyIds.IMAGE_PATH.movieKey,imagePath)
            editor?.putString(MovieKeyIds.MOVIE_ID.movieKey,movieId)
            editor?.commit()
            findNavController().navigate(R.id.action_HomeFragment_to_DetailFragment)
        }
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
}