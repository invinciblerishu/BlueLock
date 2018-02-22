package com.example.rahullavavanshi.login

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.widget.Button
import android.app.Activity
import android.view.View


public class MainActivity : AppCompatActivity()  {

    var mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var enableBtIntent: Intent? = null
    var buttonRegister: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonRegister=findViewById(R.id.buttonregistration)

        buttonRegister?.setOnClickListener({
            var clickIntent=Intent(this@MainActivity,Registration::class.java)
            startActivity(clickIntent)
        })

    }

}
