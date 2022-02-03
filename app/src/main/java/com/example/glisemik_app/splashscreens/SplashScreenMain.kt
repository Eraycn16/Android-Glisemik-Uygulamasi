package com.example.glisemik_app.splashscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.glisemik_app.MainActivity
import com.example.glisemik_app.databases.DB
import com.example.glisemik_app.databinding.ActivitySplashScreenMainBinding
import com.example.glisemik_app.objects.AppObject
import com.example.glisemik_app.parser.HtmlParser
import java.util.*

class SplashScreenMain : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DB(this)
        var dbOku = db.readUrun()
        if (dbOku.isEmpty()){
            val parse = HtmlParser(db)
            parse.titleTable()
            parse.urunlerTable()
        }


        Timer().schedule(Task(this),3000)
    }

    class Task(val activity: SplashScreenMain): TimerTask(){
        override fun run() {

            val  i = Intent(activity.applicationContext,MainActivity::class.java)
            activity.startActivity(i)
            activity.finish()
        }

    }
}