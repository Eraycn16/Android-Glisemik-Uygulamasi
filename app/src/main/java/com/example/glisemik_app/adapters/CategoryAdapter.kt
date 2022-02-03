package com.example.glisemik_app.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.glisemik_app.MainActivity
import com.example.glisemik_app.R
import com.example.glisemik_app.databases.DB
import com.example.glisemik_app.databinding.CategoryRowBinding
import com.example.glisemik_app.databinding.PopupAddBinding
import com.example.glisemik_app.databinding.UpdateCategoryRowBinding
import com.example.glisemik_app.databinding.UpdateProductRowBinding
import com.example.glisemik_app.models.KategoriModel
import com.example.glisemik_app.objects.AppObject
import com.example.glisemik_app.objects.SpinnerObj
import com.example.glisemik_app.splashscreens.SplashScreen

class CategoryAdapter(val arr: ArrayList<KategoriModel>):RecyclerView.Adapter<CategoryAdapter.MyCategoryViewHolder>() {

    class MyCategoryViewHolder(val binding: CategoryRowBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
        val binding = CategoryRowBinding.inflate(LayoutInflater.from(parent.context),parent , false)
        return MyCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {

        val  item = arr.get(position)
        holder.binding.apply {
            txtCategory.setText(item.kAdi)

            val db = DB(holder.binding.root.context)

            cardCategory.setOnLongClickListener {

                val id = item.kId
                val alert = AlertDialog.Builder(holder.binding.root.context)

                alert.setTitle("Kategori Silme işlemi!")
                alert.setMessage("Bu kategoriyi silmek istediğinizden emin misiniz?")
                alert.setNegativeButton("İptal", DialogInterface.OnClickListener { dialogInterface, i -> })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->

                    val list = db.readUrunFiltre(id!!)
                    if (list.isEmpty()){
                        val count = db.deleteKategori(id)
                        if (count>0){
                            SpinnerObj.kategoriIndex=position+1
                            arr.removeAt(position)
                            notifyItemChanged(position)
                            AppObject.splashFlag = true
                            val i = Intent(holder.binding.root.context,SplashScreen::class.java)
                            i.putExtra("okay","Kategori silme işlemi başarılı!")
                            holder.binding.root.context.startActivity(i)
                        }else{
                            val i = Intent(holder.binding.root.context,SplashScreen::class.java)
                            i.putExtra("err","Kategori silme işlemi başarısız!")
                            holder.binding.root.context.startActivity(i)
                        }
                    }else{
                        val i = Intent(holder.binding.root.context,SplashScreen::class.java)
                        i.putExtra("err","Silmek istediğiniz kategoride hala ürün bulunmaktadır!")
                        holder.binding.root.context.startActivity(i)
                    }

                })
                alert.show()

                true
            }

            cardCategory.setOnClickListener {
                val id = item.kId

                val bindPop = UpdateCategoryRowBinding.inflate(LayoutInflater.from(holder.binding.root.context))
                val alert = AlertDialog.Builder(holder.binding.root.context,
                    com.google.android.material.R.style.Animation_AppCompat_Dialog)

                bindPop.upKategoriAdi.setText(item.kAdi)
                alert.setTitle("Kategori Güncelleme işlemi!")
                alert.setNegativeButton("İptal", DialogInterface.OnClickListener { dialogInterface, i -> })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->

                    val yeniKAdi = bindPop.upKategoriAdi.text.toString()
                    if (bindPop.upKategoriKontrol.isChecked){
                        if (yeniKAdi.isNotEmpty()){

                            val count = db.updateKategori(id!!,yeniKAdi)
                            if (count>0){
                                AppObject.splashFlag = true
                                val intent = Intent(holder.binding.root.context,SplashScreen::class.java)
                                intent.putExtra("okay","Kategori adı başarılı bir şekilde güncellendi!")
                                holder.binding.root.context.startActivity(intent)
                            }else{
                                val intent = Intent(holder.binding.root.context,SplashScreen::class.java)
                                intent.putExtra("err","Kategori güncelleme işlemi başarısız!")
                                holder.binding.root.context.startActivity(intent)
                            }

                        }else{
                            val intent = Intent(holder.binding.root.context,SplashScreen::class.java)
                            intent.putExtra("err","Kategori adı boş bırakılamaz!")
                            holder.binding.root.context.startActivity(intent)
                        }
                    }else{
                        val intent = Intent(holder.binding.root.context,SplashScreen::class.java)
                        intent.putExtra("err","Kategoriyi güncellemek için onay vermeniz gerekir!")
                        holder.binding.root.context.startActivity(intent)
                    }

                })
                alert.setView(bindPop.root)
                alert.show()
            }
        }
    }

    override fun getItemCount(): Int {
      return arr.size
    }
}