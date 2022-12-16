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
import androidx.recyclerview.widget.LinearLayoutManager
import ch.mov.cinema.cinemaapp.model.MovieDataViewModel
import ch.mov.cinema.cinemaapp.model.adapter.MainCategoryAdapter
import ch.mov.cinema.cinemaapp.model.adapter.SubCategoryAdapter
import ch.mov.cinema.cinemaapp.model.entities.Items
import ch.mov.cinema.cinemaapp.model.entities.Main
import ch.mov.cinema.cinemaapp.model.entities.MainCategory
import ch.mov.cinema.cinemaapp.model.entities.Movies
import ch.mov.cinema.databinding.FragmentHomeBinding
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var arrMainCategory = ArrayList<Movies>()
    var arrSubCategory = ArrayList<Movies>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         *  my implementation
         */

        val model: MovieDataViewModel by activityViewModels()
        model.initDB(requireContext())

        lifecycleScope.launchWhenStarted{
            withContext(Dispatchers.Default){
                model.readMovies()
            }
        }

        /**
         * Test API
         */
        testAPIMain(requireContext())
        testAPISub()

        var mainCategoryAdapter = MainCategoryAdapter()
        var subCategoryAdapter = SubCategoryAdapter()

        mainCategoryAdapter.setData(arrMainCategory)
        subCategoryAdapter.setData(arrSubCategory)

        binding.rvSubCategory.adapter = subCategoryAdapter
        binding.rvMainCategory.adapter = mainCategoryAdapter


        binding.rvMainCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        binding.rvSubCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


        var text :String
        val url = "https://imdb-api.com/en/API/ComingSoon/k_dpwxdc3v"
        val url2 = "https://www.themealdb.com/api/json/v1/1/categories.php"
//        var categories :Items? = null
        /**
         * Actual API

        //        val requestQueue = Volley.newRequestQueue(requireContext())
        //        val request = StringRequest(Request.Method.GET, url, { response ->
        //           var categories = Klaxon().parse<Items>(response)
        //                for(category in categories?.items!!){
        //                    var id : Int = category.id.subSequence(2,category.id.length).toString().toInt()
        //                    arrSubCategory.add(Recipes(id,category.title,category.image))
        //                }
        //            mainCategoryAdapter.setData(arrMainCategory)
        //            subCategoryAdapter.setData(arrSubCategory)
        //
        //            binding.rvSubCategory.adapter = subCategoryAdapter
        //            binding.rvMainCategory.adapter = mainCategoryAdapter
        //        },
        //            {
        //
        //            })
        //
        //        requestQueue.add(request)
         */

//        model.recipes.observe(viewLifecycleOwner,
//            // note that the observer sends the new value as parameter
//            Observer<MutableList<Recipes>>{newVal ->
////                adapter?.clear()
////                adapter?.addAll(newVal) // addAll will call notifyDatasetChanged
//
//            })


        /**
         *  my implementation
         */

        //binding.buttonSecond.setOnClickListener {
        //  findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        //}
    }

    fun testAPISub(){
        val inputStream = requireContext().resources.openRawResource(R.raw.commingup)
        val categories = Klaxon().parse<Items>(inputStream)

        for(category in categories?.items!!){
            var id : Int = category.id.subSequence(2,category.id.length).toString().toInt()
            arrSubCategory.add(Movies(id,category.title,category.image))
        }
    }

    fun testAPIMain(context : Context){
        val inputStream = requireContext().resources.openRawResource(R.raw.maincategory)
        val categories = Klaxon().parse<Main>(inputStream)


        for(maincategories in categories?.maincategories!!){
            var resourceId = context.getResources().getIdentifier(maincategories.icon, "drawable", context.getPackageName()).toString();
            arrMainCategory.add(Movies(maincategories.m_id.toInt(),maincategories.title,resourceId))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }
}