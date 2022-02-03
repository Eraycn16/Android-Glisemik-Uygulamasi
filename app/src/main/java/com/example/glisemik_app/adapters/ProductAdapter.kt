package com.example.glisemik_app.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.glisemik_app.R
import com.example.glisemik_app.databases.DB
import com.example.glisemik_app.databinding.PopupAddBinding
import com.example.glisemik_app.databinding.ProductRowBinding
import com.example.glisemik_app.databinding.UpdateProductRowBinding
import com.example.glisemik_app.models.UrunlerModel
import com.example.glisemik_app.objects.AppObject
import com.example.glisemik_app.splashscreens.SplashScreen

class ProductAdapter(val arrList: ArrayList<UrunlerModel>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    class MyViewHolder(val bind: ProductRowBinding): RecyclerView.ViewHolder(bind.root){}

    //private var listBack:ArrayList<UrunlerModel> = arrList
   // lateinit var kategorid:ArrayList<Int>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val bind = ProductRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(bind)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = arrList.get(position)

        holder.bind.apply {
            txtGlisemik.setText(item.uGlisemik)
            txtUrun.setText(item.uAdi)
            txtKalori.setText(item.uKal)
            txtKarbon.setText(item.uKMiktari)

/*
            val deneme:Int = txtGlisemik.text.toString().toInt()
           // val glisemikBackground: Int = item.uGlisemik!!.toString().toInt()

            // item.uGlisemik.toString().toInt()
            if (deneme > 50){
                Log.d("TAG", "onBindViewHolder:"+txtUrun.text +" " + txtGlisemik.text)
              //  txtGlisemik.setTextColor(R.color.red)
                productLay.setBackgroundColor(R.color.red)
            }
 */

            val db = DB(holder.bind.root.context)

            listCard.setOnLongClickListener {
                val id = item.uId
                val alert = AlertDialog.Builder(holder.bind.root.context)

                alert.setTitle("Ürün Silme işlemi!")
                alert.setMessage("Bu ürünü silmek istediğinizden emin misiniz?")
                alert.setNegativeButton("İptal", DialogInterface.OnClickListener { dialogInterface, i -> })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->

                    val count = db.deleteUrun(id!!)
                    if (count>0){
                        arrList.removeAt(position)
                        notifyItemChanged(position)
                    }else{
                      //  Toast.makeText(holder.bind.root.context, "Ürün silinemedi!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(holder.bind.root.context,SplashScreen::class.java)
                        intent.putExtra("err","Bu ürünü işlemi başarısız!")
                        holder.bind.root.context.startActivity(intent)
                    }
                })
                alert.show()

                true
            }

            listCard.setOnClickListener {
                val id = item.uId
                var kid = item.kId
                val bindPop = UpdateProductRowBinding.inflate(LayoutInflater.from(holder.bind.root.context))
                val alert = AlertDialog.Builder(holder.bind.root.context,
                    com.google.android.material.R.style.Animation_AppCompat_Dialog)

                val categoryList = categorySpinner(holder.bind.root.context)
                val spinAdapter = ArrayAdapter<String>(holder.bind.root.context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,categoryList)

                bindPop.upUAdi.setText(item.uAdi)
                bindPop.upUGlisemik.setText(item.uGlisemik)
                bindPop.upUKalori.setText(item.uKal)
                bindPop.upUKhidrat.setText(item.uKMiktari)
                bindPop.upSpinner.adapter = spinAdapter


                bindPop.upSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val catId = categoryId(holder.bind.root.context)


                        if (p2 != kid && p2 != 0){
                            kid = catId.get(p2)
                        }

                       // Log.d("DENEME", "onItemSelected: $kid")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

                alert.setTitle("Ürün Güncelleme işlemi!")
                alert.setMessage("Bu ürünü güncellemek istediğinizden emin misiniz?")
                alert.setNegativeButton("İptal", DialogInterface.OnClickListener { dialogInterface, i -> })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->

                    val uAdi = bindPop.upUAdi.text.toString()
                    val uGlisemik = bindPop.upUGlisemik.text.toString()
                    val uKalori = bindPop.upUKalori.text.toString()
                    val uKhidrat = bindPop.upUKhidrat.text.toString()
                    if (uAdi.isNotEmpty()){
                        if (uGlisemik.isNotEmpty()){
                            if (uKalori.isNotEmpty()){
                                if (uKhidrat.isNotEmpty()){
                                    val insert = db.updateUrun(id!!,uAdi,uGlisemik,uKhidrat,uKalori,kid!!)

                                    if (insert>0) {
                                        AppObject.splashFlag=true
                                        val intent = Intent(holder.bind.root.context,SplashScreen::class.java)
                                        intent.putExtra("okay","Ürün verileri başarılı bir şekilde güncellenmiştir!")
                                        holder.bind.root.context.startActivity(intent)
                                    }else{
                                        val intent = Intent(holder.bind.root.context,SplashScreen::class.java)
                                        intent.putExtra("err","Ürün verilerini güncelleme işlemi başarısız!")
                                        holder.bind.root.context.startActivity(intent)
                                    }
                                }else{
                                    val intent = Intent(holder.bind.root.context,SplashScreen::class.java)
                                    intent.putExtra("err","Ürün karbonhidrat miktarını boş bırakamazsınız!")
                                    holder.bind.root.context.startActivity(intent)
                                }

                            }else{
                                val intent = Intent(holder.bind.root.context,SplashScreen::class.java)
                                intent.putExtra("err","Ürün kalorisini boş bırakamazsınız!")
                                holder.bind.root.context.startActivity(intent)
                            }

                        }else{
                            val intent = Intent(holder.bind.root.context,SplashScreen::class.java)
                            intent.putExtra("err","Ürün Glisemik indeksi alanı boş bırakılamaz!")
                            holder.bind.root.context.startActivity(intent)
                        }
                    }else{
                        val intent = Intent(holder.bind.root.context,SplashScreen::class.java)
                        intent.putExtra("err","Ürün adı boş bırakılamaz!")
                        holder.bind.root.context.startActivity(intent)
                    }


                })

                alert.setView(bindPop.root)
                alert.show()
            }

        }
    }

    override fun getItemCount(): Int {
      return arrList.size
    }

    fun categorySpinner(context: Context): ArrayList<String>{
        val db = DB(context)
        val list = db.readKategori()
        val kategoriNames = ArrayList<String>()

        kategoriNames.add("Kategori Seçiniz..")
        for (item in list){
            val kategori = item.kAdi
            kategoriNames.add(kategori!!)
        }
        return kategoriNames
    }

    fun categoryId(context: Context): ArrayList<Int>{
        val db = DB(context)
        val list = db.readKategori()
        val kategoriId = ArrayList<Int>()
        kategoriId.add(0)
        for (item in list){
            kategoriId.add(item.kId!!)
        }
        return kategoriId
    }

}