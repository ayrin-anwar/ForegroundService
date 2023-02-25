package com.example.foregroundservice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.foregroundservice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var receiver: BootReceiver
    private lateinit var filter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receiver = BootReceiver()
        filter = IntentFilter()
        filter.addAction(Intent.ACTION_BOOT_COMPLETED)
        registerReceiver(receiver,filter)
        binding.startBt.setOnClickListener {
            startStopService()
        }
    }
    private fun startStopService() {

        if(isMyServiceRunning(ForegroundService::class.java)) {
            Toast.makeText(this,"service stopped",Toast.LENGTH_SHORT).show()
            stopService(Intent(this,ForegroundService::class.java))

        } else {
            Toast.makeText(this,"service started",Toast.LENGTH_SHORT).show()

            startService(Intent(this,ForegroundService::class.java))
        }
    }
    private fun isMyServiceRunning(mClass:Class<ForegroundService>):Boolean {
        val manager:ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service:ActivityManager.RunningServiceInfo in manager.getRunningServices(Int.MAX_VALUE)) {
            if(mClass.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }
}




