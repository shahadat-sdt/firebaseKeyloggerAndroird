package com.sm.sdt.firebasekeyloggerandroird

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sm.sdt.firebasekeyloggerandroird.ui.Fragment.LoginFragmentDirections
import java.util.regex.Pattern


object ConstFun {

    fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

     fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun loginFirebase(
        firebaseAuth: FirebaseAuth,
        emailText: String,
        passwordText: String,
        activity: Context
    ) {
        firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        activity,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                }else {
                    Toast.makeText(
                        activity,
                        "Incorrect Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}