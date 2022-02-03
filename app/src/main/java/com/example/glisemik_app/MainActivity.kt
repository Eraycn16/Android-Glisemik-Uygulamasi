package com.example.glisemik_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.glisemik_app.databases.DB
import com.example.glisemik_app.databinding.ActivityMainBinding
import com.example.glisemik_app.models.KategoriModel
import com.example.glisemik_app.objects.AppObject
import com.example.glisemik_app.objects.SpinnerObj
import com.example.glisemik_app.parser.HtmlParser
import com.example.glisemik_app.splashscreens.SplashScreen
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var db:DB
    lateinit var spinAdapter:ArrayAdapter<String>
    var kategoriNames = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setupNav()
        setContentView(binding.root)

        db = DB(this)


        val spinList = db.readKategori()
        var kategoriIdList = ArrayList<Int>()
        kategoriIdList.add(0)
        kategoriNames.add("Hepsi")
        for (item in spinList){
            val kategori = item.kAdi
            kategoriNames.add(kategori!!)
            kategoriIdList.add(item.kId!!)
        }

        spinAdapter = ArrayAdapter<String>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,kategoriNames)
        binding.spinner.adapter = spinAdapter
        spinAdapter.notifyDataSetChanged()

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               // Toast.makeText(applicationContext, "$p2", Toast.LENGTH_SHORT).show()
                AppObject.kategoriId = kategoriIdList.get(p2)
                val listFragment = ListFragment()
                changeFragment(listFragment)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT).show()
            }

        }


    }

    /*
    var dataSpinner: KategoriModel by Delegates.observable(KategoriModel()) {
            property, oldValue, newValue ->
        // println( "" + newValue. + " " + newValue.title )
        kategoriNames.add(newValue.kAdi.toString())
    }
     */


    fun setupNav(){

        actionBarToggle = ActionBarDrawerToggle(this,binding.drawerNav,binding.actBar.actionBar,0,0)
        binding.drawerNav.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.itemList ->{
                    binding.spinner.visibility = View.VISIBLE
                    val listFragment = ListFragment()
                    changeFragment(listFragment)
                    binding.drawerNav.close()
                    true
                }
                R.id.itemAddCategory->{
                    binding.spinner.visibility = View.INVISIBLE
                    val category = CategoryFragment()
                    changeFragment(category)
                    binding.drawerNav.close()
                    true
                }
                R.id.itemAddProduct ->{
                    binding.spinner.visibility = View.INVISIBLE
                    val pro = AddProductFragment()
                    changeFragment(pro)
                    binding.drawerNav.close()
                    true
                }
                R.id.itemExit ->{
                    finish()
                    true
                }
                else ->{
                    false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "onStart: 1")
        val yeniKategoriName = SpinnerObj.kategoriName
        val kategoriIndex = SpinnerObj.kategoriIndex
        if (yeniKategoriName.isNotEmpty()){
            kategoriNames.add(yeniKategoriName)
            SpinnerObj.kategoriName=""
        }
        if (kategoriIndex != 0){
            kategoriNames.removeAt(kategoriIndex)
            SpinnerObj.kategoriIndex = 0
        }

        spinAdapter.notifyDataSetChanged()
    }

    fun changeFragment(fragment: Fragment){
        val tra = supportFragmentManager.beginTransaction()
        tra.replace(binding.frame.id,fragment)
        tra.commit()

    }
}