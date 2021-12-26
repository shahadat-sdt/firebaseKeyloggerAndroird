package com.sm.sdt.firebasekeyloggerandroird

import java.text.SimpleDateFormat
import java.util.*

object ConstFun {

    fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
        return dateFormat.format(Calendar.getInstance().time)
    }
}