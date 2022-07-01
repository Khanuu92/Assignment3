package com.example.bilalhamid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.bilalhamid.R
import com.example.bilalhamid.api.ApiServiceClassic
import com.example.bilalhamid.database.DatabseHelper
import com.example.bilalhamid.model.MusicModel
import com.example.bilalhamid.model.ResultMusicModel
import com.example.bilalhamid.view.MainActivity
import com.example.bilalhamid.view.MusicAdapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RockFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RockFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rock_, container, false)
        val rvrock: RecyclerView = view.findViewById(R.id.rv_Rock)
        val swipeRock: SwipeRefreshLayout = view.findViewById(R.id.swipe_Rock)
        val db: DatabseHelper = DatabseHelper(requireContext())
        val musicAdapter1: MusicAdapter

        rvrock.layoutManager = LinearLayoutManager(activity)

        // startRetrofit(db)
        val listROCK = db.retrivePopMusic("Rock")
        musicAdapter1 = MusicAdapter(listROCK)
        rvrock.adapter = musicAdapter1

        swipeRock.setOnRefreshListener {
            if (MainActivity.isOnline(requireContext())) {
                startRetrofit(db)
            }
            else{
                MainActivity.makeToastmsg("No Internet",requireContext())
            }
        }
        return view
    }

    private fun startRetrofit(
        db: DatabseHelper)

       {
        var list1: List<ResultMusicModel>
        ApiServiceClassic.createRetrofit().create(ApiServiceClassic::class.java).getRockMusic()
            .enqueue(object : Callback<MusicModel> {

                override fun onResponse(
                    call: Call<MusicModel>,
                    response: Response<MusicModel>
                ) {
                    if (response.isSuccessful) {

                        db.deleteAll("Rock")

                        list1 = response.body()!!.results
                        for (i in 0..45) {
                            db.insertAll(
                                "Rock",
                                list1.get(i).artistName,
                                list1.get(i).collectionName,
                                list1.get(i).artworkUrl100,
                                list1.get(i).trackPrice.toString(),
                                list1.get(i).previewUrl
                            )
                        }

                    }

                }

                override fun onFailure(call: Call<MusicModel>, t: Throwable) {
                    Toast.makeText(activity, "Error Occurred! " +t.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Rock_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RockFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}