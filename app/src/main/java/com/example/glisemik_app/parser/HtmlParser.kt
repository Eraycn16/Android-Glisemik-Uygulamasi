package com.example.glisemik_app.parser

import android.util.Log
import android.widget.Toast
import com.example.glisemik_app.databases.DB
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Exception
import kotlin.math.log

class HtmlParser(val db: DB) {

    val url = "http://kolaydoktor.com/saglik-icin-yasam/diyet-ve-beslenme/besinlerin-glisemik-indeks-tablosu/0503/1"


    fun titleTable(){

        Thread(Runnable {
            try {

                val doc:Document = Jsoup.connect(url).get()
                val elements: Elements = doc.select("table").select("tbody")

                for (element in elements){
                    Log.d("TAG", "titleTable: "+element.childrenSize())
                    val data = element.select("td").get(0).select("b").text()
                    if (data.isNotEmpty()){
                       // Log.d("TAG2", "titleTable: "+ data)
                        db.addData(data)
                    }
                }
                //val sutun = elements.select("tr").get(1).select("b").text();
               // Log.d("TAG2", "titleTable: "+ sutun)
                // Sutun değişkenleri parçalara bölünerek tek bir defa çağırılmalı

            }catch (e: Exception){
                Log.d("TAG", "titleTable: Hata")
            }
        }).start()
    }


    fun urunlerTable(){

        Thread(Runnable {
            try{
                val doc:Document = Jsoup.connect(url).get()
                val elements: Elements = doc.select("table").select("tbody")
                var count = 0
                for (element in elements){
                    //Log.d("TAG", "titleTable: "+element.childrenSize())
                        count = count+1
                    for (item in 2..element.childrenSize()-1){

                        val items = element.select("tr").get(item).text()
                        if (items.isNotEmpty()){
                            val itemName = element.select("tr").get(item).select("td").get(0).select("b").text()
                            val itemGlisemik =  element.select("tr").get(item).select("td").get(1).select("b").text()
                            val itemMiktar =  element.select("tr").get(item).select("td").get(2).select("b").text()
                            val itemKalori =  element.select("tr").get(item).select("td").get(3).select("b").text()
                            val id = count
                            db.addDataUrun(itemName,itemGlisemik,itemMiktar,itemKalori,id)
                          //  Log.d("TAG", "titleTable: "+it)
                           // Log.d("TAG","----"+item)
                        }

                    }
                }
            }catch (e:Exception){
                Log.d("TAG", "urunlerTable: ")
            }

        }).start()
    }


}