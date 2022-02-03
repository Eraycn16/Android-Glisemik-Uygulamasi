package com.example.glisemik_app.databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.glisemik_app.models.KategoriModel
import com.example.glisemik_app.models.UrunlerModel

class DB(context: Context?, name: String?="glisemik.db", factory: SQLiteDatabase.CursorFactory?=null, version: Int = 1)
    :SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(p0: SQLiteDatabase?) {

        p0!!.execSQL("CREATE TABLE \"Kategori\" (\n" +
                "\t\"kId\"\tINTEGER,\n" +
                "\t\"kAdi\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"kId\" AUTOINCREMENT)\n" +
                ");")

        p0.execSQL("CREATE TABLE \"Urunler\" (\n" +
                "\t\"uId\"\tINTEGER,\n" +
                "\t\"uAdi\"\tTEXT,\n" +
                "\t\"uGlisemik\"\tTEXT,\n" +
                "\t\"uKMiktari\"\tTEXT,\n" +
                "\t\"uKal\"\tTEXT,\n" +
                "\t\"kId\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"kId\") REFERENCES \"Kategori\"(\"kId\"),\n" +
                "\tPRIMARY KEY(\"uId\" AUTOINCREMENT)\n" +
                ");")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS Kategori")
        p0!!.execSQL("DROP TABLE IF EXISTS Urunler")

        onCreate(p0)
    }

    fun addData(name: String):Long{
        val write = this.writableDatabase
        val conVal = ContentValues()
        conVal.put("kAdi",name)

        val insertVal = write.insert("Kategori",null,conVal)
        return insertVal
    }

    fun addDataUrun(name: String,glisemik:String,miktar:String,kalori:String,kId:Int):Long{

        val write = this.writableDatabase
        val conValue = ContentValues()
        conValue.put("uAdi",name)
        conValue.put("uGlisemik",glisemik)
        conValue.put("uKMiktari",miktar)
        conValue.put("uKal",kalori)
        conValue.put("kId",kId)

        val insertVal = write.insert("Urunler",null,conValue)

        return insertVal
    }

    fun readUrun():ArrayList<UrunlerModel>{

        val arr = ArrayList<UrunlerModel>()
        val read = this.readableDatabase

        val querySql = "select * from Urunler"

        val cursor = read.rawQuery(querySql,null)

        while (cursor.moveToNext()){

            val id = cursor.getInt(0)
            val uAd = cursor.getString(1)
            val uGlisemik = cursor.getString(2)
            val uKMiktari = cursor.getString(3)
            val uKal = cursor.getString(4)
            val kId = cursor.getInt(5)

            val p = UrunlerModel(id,uAd,uGlisemik, uKMiktari, uKal, kId)
            arr.add(p)
        }

        return arr
    }
    fun readUrunFiltre(kId:Int):ArrayList<UrunlerModel>{

        val arr = ArrayList<UrunlerModel>()
        val read = this.readableDatabase

        val querySql = "select * from Urunler where kId="+kId

        val cursor = read.rawQuery(querySql,null)

        while (cursor.moveToNext()){

            val id = cursor.getInt(0)
            val uAd = cursor.getString(1)
            val uGlisemik = cursor.getString(2)
            val uKMiktari = cursor.getString(3)
            val uKal = cursor.getString(4)
            val kId = cursor.getInt(5)

            val p = UrunlerModel(id,uAd,uGlisemik, uKMiktari, uKal, kId)
            arr.add(p)
        }

        return arr
    }

    fun readKategori():ArrayList<KategoriModel>{
        val arr = ArrayList<KategoriModel>()
        val read = this.readableDatabase
        val querySql = "select * from Kategori"
        val cursor = read.rawQuery(querySql,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(0)
            val name = cursor.getString(1)

            val kategori = KategoriModel(id,name)
            arr.add(kategori)
        }
        return arr
    }

    fun deleteKategori(kid:Int):Int{
        val deleted = this.writableDatabase
        val count = deleted.delete("Kategori","kId="+kid,null)

        return count
    }
    fun deleteUrun(uId:Int):Int{
        val deleted = this.writableDatabase
        val count = deleted.delete("Urunler","uId="+uId,null)

        return count
    }

    fun updateUrun(uId: Int,uName:String,uGlisemik:String, uKMiktari:String, uKal:String,kId: Int):Int{

        val up = this.writableDatabase
        val conValues = ContentValues()

        conValues.put("uAdi",uName)
        conValues.put("uGlisemik",uGlisemik)
        conValues.put("uKMiktari",uKMiktari)
        conValues.put("uKal",uKal)
        conValues.put("kId",kId)

        val count = up.update("Urunler",conValues,"uId="+uId,null)
        return count
    }

    fun updateKategori(kId:Int,kAdi:String):Int{

        val upKategori = this.writableDatabase
        val conValues = ContentValues()
        conValues.put("kAdi",kAdi)
        val count = upKategori.update("Kategori",conValues,"kId="+kId,null)
        return count
    }

}