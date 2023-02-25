package com.example.foregroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast


class BootReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val isAirplaneModeEnabled = p1?.getBooleanExtra("state", false) ?: return
        if (Intent.ACTION_BOOT_COMPLETED == p1.getAction()) {
            val syncServiceIntent = Intent(p0, ForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                p0?.startForegroundService(syncServiceIntent)
            }
        }
        if (isAirplaneModeEnabled) {
            Toast.makeText(p0,"boot receiver started",Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(p0, "boot receiver stopped", Toast.LENGTH_LONG).show()
        }
        TODO("Not yet implemented")
    }
}