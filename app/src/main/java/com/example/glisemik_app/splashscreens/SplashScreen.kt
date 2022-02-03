package com.example.glisemik_app.splashscreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.glisemik_app.databinding.ActivitySplashScreenBinding
import com.example.glisemik_app.objects.AppObject
import java.util.*

class SplashScreen : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AppObject.splashFlag == true){
            val uriOkay = "https://media.istockphoto.com/vectors/check-mark-logo-vector-or-icon-vector-id919118254?k=20&m=919118254&s=612x612&w=0&h=nFTjCeY9bjV1unFsjJvegMvFl68P9JQTcCi9C_Pc5ZE="
            val okay = intent.getStringExtra("okay")
            binding.splashText.text = okay
            Glide.with(this).load(uriOkay).into(binding.splashIcon)
           // binding.splashIcon.setImageResource(R.drawable.okay)
            Timer().schedule(Task(this),1500)
        }else{
            val errorUri = "https://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-error-icon.png"
            Glide.with(this).load(errorUri).into(binding.splashIcon)
            val err = intent.getStringExtra("err")
            binding.splashText.text = err
          // binding.splashIcon.setImageResource(R.drawable.close)
            Timer().schedule(Task(this),1500)
        }

    }

    class Task(val activity: SplashScreen): TimerTask(){
        override fun run() {

            if (AppObject.splashFlag==true){
                AppObject.splashFlag=false
            }

            activity.finish()
        }

    }
}