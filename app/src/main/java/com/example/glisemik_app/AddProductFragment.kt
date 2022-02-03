package com.example.glisemik_app

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.glisemik_app.databases.DB
import com.example.glisemik_app.databinding.FragmentAddProductBinding
import com.example.glisemik_app.objects.AppObject
import com.example.glisemik_app.splashscreens.SplashScreen
import com.google.android.material.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddProductFragment : Fragment() {
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

    var kategoriId:Int = 0
    private lateinit var binding: FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddProductBinding.inflate(inflater,container,false)
        val db = DB(context)

        val spinList = db.readKategori()
        val kategoriName = ArrayList<String>()
        val kategorid = ArrayList<Int>()
        kategorid.add(0)
        kategoriName.add("Kategori Seçiniz..")
        for (item in spinList){
                val kategori = "${item.kId} - ${item.kAdi}"
                kategoriName.add(kategori)
                kategorid.add(item.kId!!)

        }
        val adapter = ArrayAdapter<String>(binding.root.context,R.layout.support_simple_spinner_dropdown_item,kategoriName)
        binding.yeniUrunSpinner.adapter = adapter


        binding.yeniUrunSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                 kategoriId = kategorid.get(p2)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(context, "Kategori seçiniz..", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnAdd.setOnClickListener {

            val urunName = binding.yeniUrunAdi.text
            val urunGlisemik = binding.yeniGlisemik.text
            val urunKalori = binding.yeniKalori.text
            val urunKhidrat = binding.yeniMiktar.text

            if (urunName.isNotEmpty()){
                if (urunGlisemik.isNotEmpty()){
                    if (urunKalori.isNotEmpty()){
                        if (urunKhidrat.isNotEmpty()){
                            if (kategoriId != 0){
                               if( binding.yeniKontrol.isChecked()) {
                                   val insert = db.addDataUrun(urunName.toString(),urunGlisemik.toString(),urunKhidrat.toString(),urunKalori.toString(),kategoriId)
                                   if (insert>0){
                                       AppObject.splashFlag=true
                                       val i = Intent(context, SplashScreen::class.java)
                                       i.putExtra("okay","Ürün ekleme işlemi başarılı!")
                                       startActivity(i)
                                       binding.yeniGlisemik.text.clear()
                                       binding.yeniKalori.text.clear()
                                       binding.yeniMiktar.text.clear()
                                       binding.yeniUrunAdi.text.clear()
                                       binding.yeniUrunAdi.requestFocus()

                                   }
                               }else{
                                   val i = Intent(context, SplashScreen::class.java)
                                   i.putExtra("err","Ürün ekleme için onay vermeniz gerekir!")
                                   startActivity(i)
                               }
                            }else{
                                val i = Intent(context, SplashScreen::class.java)
                                i.putExtra("err","Kategori seçmediniz!")
                                startActivity(i)
                                binding.yeniUrunSpinner.requestFocus()
                            }
                        }else{
                            binding.yeniMiktar.error="Karbonhidrat miktarını boş bırakamazsınız!!"
                            binding.yeniMiktar.requestFocus()
                        }

                    }else{
                        binding.yeniKalori.error="Kalori değerini boş bırakamazsınız!"
                        binding.yeniKalori.requestFocus()
                    }

                }else{
                    binding.yeniGlisemik.error = "Glisemik indeksini boş bırakamazsınız!!"
                    binding.yeniGlisemik.requestFocus()
                }
            }else{
                binding.yeniUrunAdi.error = "Ürün adını boş bırakamazsın!"
                binding.yeniUrunAdi.requestFocus()
            }
        }


        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}