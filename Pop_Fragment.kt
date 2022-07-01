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
 * Use the [Pop_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Pop_Fragment : Fragment() {
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
        val view=inflater.inflate(R.layout.fragment_pop_, container, false)
        val rvPop: RecyclerView =view.findViewById(R.id.rv_pop)
        val swipePop:SwipeRefreshLayout=view.findViewById(R.id.swipe_Pop)
       // val musicAdapter2: PopMusicAdapter
        val musicAdapter2:MusicAdapter
        val db:DatabseHelper= DatabseHelper(requireContext())

        rvPop.layoutManager= LinearLayoutManager(activity)

        val listPop=db.retrivePopMusic("Pop")
        musicAdapter2 = MusicAdapter(listPop)
        rvPop.adapter = musicAdapter2
//swipe listener
        swipePop.setOnRefreshListener {
            if (MainActivity.isOnline(requireContext())) {
                startRetrofitpop(db)
            }
            else{
                MainActivity.makeToastmsg("No Internet",requireContext())
            }
        }
        //upload into recycler view

        return view
    }

    private fun startRetrofitpop(
        db: DatabseHelper

    ) {
        var list1: List<ResultMusicModel> //= ArrayList()
        ApiServiceClassic.createRetrofit().create(ApiServiceClassic::class.java).getPopMusic()
            .enqueue(object : Callback<MusicModel> {

                override fun onResponse(
                    call: Call<MusicModel>,
                    response: Response<MusicModel>
                ) {
                    if (response.isSuccessful) {

                        db.deleteAll("Pop")
                        list1 = response.body()!!.results
                        for (i in 0..45) {
                            db.insertAll(
                                "Pop",
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
         * @return A new instance of fragment Pop_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Pop_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}