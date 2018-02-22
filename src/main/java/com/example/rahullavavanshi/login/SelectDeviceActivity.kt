package com.example.rahullavavanshi.login

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_select_device.*
import org.jetbrains.anko.toast


class SelectDeviceActivity : AppCompatActivity() {

    var m_bluetoothAdapter: BluetoothAdapter? =null
    lateinit var m_pairedDevices: Set<BluetoothDevice>
    val REQUSET_ENABLE_BLUETOOTH= 1
    companion object {
        val EXTRA_ADDRESS: String="Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device)
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter==null)
            toast("This Device Dosetn't Support Bluetooth")
        if(!m_bluetoothAdapter!!.isEnabled)
        {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent,REQUSET_ENABLE_BLUETOOTH)
        }
        select_device_refresh.setOnClickListener{pairedDeviceList()}
    }
    private fun pairedDeviceList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list: ArrayList<BluetoothDevice> = ArrayList()

        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device", "" + device)
            }
        } else {
            toast("OOPS!No Paired Devices Found..." +
                    "Try Refresh OR Check BlueLock Is Turned On")
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        select_device_list.adapter = adapter
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUSET_ENABLE_BLUETOOTH){
            if(requestCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    toast("Bluetooth Has Been Enabled")
                } else {
                    toast("Bluetooth Has Been Disabled")
                }
            }
                else if(resultCode == Activity.RESULT_CANCELED){
                    toast("Bluettoth enabled has been cancelled")
                }

        }
    }

