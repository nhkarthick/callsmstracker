package com.ner.calltracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.widget.Toast


class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras

            val aObject = bundle?.get("pdus") as Array<*>
            var smsMessage: SmsMessage? = null
            smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val format = bundle.getString("format")
                SmsMessage.createFromPdu(aObject[0] as ByteArray?, format)
            } else {
                SmsMessage.createFromPdu(aObject[0] as ByteArray?)
            }
            val sender = "Sender : " + smsMessage.displayOriginatingAddress
            val message = "Sender : " + smsMessage.displayMessageBody
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        }
    }
}