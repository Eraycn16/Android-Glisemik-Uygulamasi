package com.example.glisemik_app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glisemik_app.adapters.CategoryAdapter
import com.example.glisemik_app.databases.DB
import com.example.glisemik_app.databinding.FragmentCategoryBinding
import com.example.glisemik_app.databinding.PopupAddBinding
import com.example.glisemik_app.models.KategoriModel
import com.example.glisemik_app.objects.AppObject
import com.example.glisemik_app.objects.SpinnerObj
import com.example.glisemik_app.splashscreens.SplashScreen

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CategoryFragment : Fragment() {
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

    private lateinit var binding: FragmentCategoryBinding
    lateinit var adapter:CategoryAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryBinding.inflate(inflater,container,false)

        binding.CategoryList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val db =DB(context)

        binding.btnAddCategory.setOnClickListener {

            val bindPop = PopupAddBinding.inflate(LayoutInflater.from(context))
            val popup = AlertDialog.Builder(context, R.style.AlertDialogCustom)

            popup.setTitle("Yeni Kategori Ekleme işlemi!")
            popup.setNegativeButton("İptal", DialogInterface.OnClickListener { dialogInterface, i -> })
            popup.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
              //  val db = DB(context)
                val newKategori = bindPop.newCategoryName.text
                if (newKategori.isNotEmpty()){
                    val insertVal = db.addData(newKategori.toString())
                    if (insertVal > 0){
                        SpinnerObj.kategoriName = newKategori.toString()
                        AppObject.splashFlag = true
                        val i = Intent(context,SplashScreen::class.java)
                        i.putExtra("okay","Yeni kategori ekleme işlemi başarılı!")
                        startActivity(i)
                    }else{
                        val i = Intent(context,SplashScreen::class.java)
                        i.putExtra("err","Yeni kategori ekleme işlemi başarısız!")
                        startActivity(i)
                    }
                }else{
                    val i = Intent(context,SplashScreen::class.java)
                    i.putExtra("err","Lütfen eklemek istediğiniz kategorinin ismini giriniz!")
                    startActivity(i)
                }
            })
            popup.setView(bindPop.root)
            popup.show()

        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val db =DB(context)
        val list = db.readKategori()
        adapter = CategoryAdapter(list)
        binding.CategoryList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}