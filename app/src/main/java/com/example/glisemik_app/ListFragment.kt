package com.example.glisemik_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glisemik_app.adapters.ProductAdapter
import com.example.glisemik_app.databases.DB
import com.example.glisemik_app.databinding.FragmentListBinding
import com.example.glisemik_app.models.UrunlerModel
import com.example.glisemik_app.objects.AppObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
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

    private lateinit var binding: FragmentListBinding
   // private val bind get() = binding!!
   lateinit var list:ArrayList<UrunlerModel>
   lateinit var tempList:ArrayList<UrunlerModel>
   val deger = AppObject.kategoriId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater,container,false)
        binding.listReyc.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
     //   val db = DB(this.context)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

              return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                tempList.clear()
                val searchTxt = p0!!.lowercase()
                if (searchTxt.isNotEmpty()){

                    list.forEach {
                        if (it.uAdi!!.lowercase().contains(searchTxt)){
                            tempList.add(it)
                        }
                        if(it.uGlisemik!!.contains(searchTxt) || it.uKMiktari!!.contains(searchTxt) || it.uKal!!.contains(searchTxt)){
                            tempList.add(it)
                           }
                    }
                    binding.listReyc.adapter!!.notifyDataSetChanged()
                }else{

                    tempList.clear()
                    tempList.addAll(list)
                    binding.listReyc.adapter!!.notifyDataSetChanged()
                }

               return false
            }

        })

        binding.txtGlisemikBaslik.setOnClickListener {
            Toast.makeText(binding.root.context, "Glisemik Değeri(100g) için", Toast.LENGTH_SHORT).show()
        }
        binding.txtKarbonBaslik.setOnClickListener {
            Toast.makeText(binding.root.context, "Karbonhidrat miktarı (100 g'daki)", Toast.LENGTH_SHORT).show()
        }
        binding.txtKaloriBaslik.setOnClickListener {
            Toast.makeText(binding.root.context, "Kalori (100 g'daki)", Toast.LENGTH_SHORT).show()
        }

        return binding.root

        //adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()

        val db = DB(this.context)
        tempList = arrayListOf()

        if (deger==0){
            list = db.readUrun()
        }else{

            list = db.readUrunFiltre(deger)
        }
        tempList.addAll(list)
        val adapter = ProductAdapter(tempList)
        binding.listReyc.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}