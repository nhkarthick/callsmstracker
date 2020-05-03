package com.ner.calltracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast


class PhoneCallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.PHONE_STATE") {
            try {
                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                val incomingNumber =
                    intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                    Toast.makeText(
                        context,
                        "Ringing Number is - $incomingNumber",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
