package com.sm.sdt.firebasekeyloggerandroird

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Consts {
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()


    const val TAG = "Keylogger_tag"
    const val USER_REFRENCE = "users"
    const val DATA_REFRENCE = "data"
    const val ACCOUNT_DATA = "account data"
    const val DEVICE_NAME = "Device Name"
    val DEVICE_MODEL = android.os.Build.MODEL
    const val PREFERENCE_NAME = "SharedPreference"
    const val CHILD_NAME_KEY = "ChildName"
    const val USER_TYPE = "user_type"
    const val KEYLOG_DATA = "keylogData"
    const val IS_HIDDEN = "Is_Hidden"
    const val IS_ACCESSIBILITY_ACTIVE = "Is_Accessibility_Service_Active"
}