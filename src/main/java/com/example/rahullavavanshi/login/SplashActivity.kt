package com.example.rahullavavanshi.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import com.felipecsl.gifimageview.library.GifImageView
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

class SplashActivity : AppCompatActivity() {

        var inputStream:InputStream?=null
        var gifImageView:GifImageView? = null
        var progressBar:ProgressBar?=null
        var permissionsString=arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.ACCESS_COARSE_LOCATION)

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

            gifImageView=findViewById<GifImageView>(R.id.splashGif)
            progressBar=findViewById<ProgressBar>(R.id.progBar)
            progressBar?.visibility = VISIBLE
            try {
                inputStream=getAssets().open("Gif.gif")
                var bytes = IOUtils.toByteArray(inputStream)
                gifImageView?.setBytes(bytes)
                gifImageView?.startAnimation()
            }catch (e: IOException){
                e.printStackTrace()
            }

            if(hasPermissions(this@SplashActivity,*permissionsString)){
                //asking for permission
                ActivityCompat.requestPermissions(this@SplashActivity,permissionsString,131)

            }else{
                //killing splash and launching main activity
                Handler().postDelayed({
                    var startAct =Intent(this@SplashActivity,MainActivity::class.java)
                    startActivity(startAct)
                    this.finish()
                },1000)
            }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            131-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4]== PackageManager.PERMISSION_GRANTED) {
                    Handler().postDelayed({
                        var startAct =Intent(this@SplashActivity,MainActivity::class.java)
                        startActivity(startAct)
                        this.finish()
                    },1000)
                }
                else{
                    Toast.makeText(this@SplashActivity,"Please Grant All Permissions",Toast.LENGTH_SHORT).show()
                    this.finish()
                }

                return
            }
            else->{
                Toast.makeText(this@SplashActivity, "Something's wrong", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }
        }
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean{
        var hasAllPermissions = true
        for(permission in permissions){
            val res = context.checkCallingOrSelfPermission(permission)
            if(res != PackageManager.PERMISSION_GRANTED){
                hasAllPermissions=false
            }
        }
        return hasAllPermissions
    }
}
