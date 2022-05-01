package com.nlv2022.trenaghorik.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    @SuppressLint("SimpleDateFormat")// говорю что мне это не важно))))
    val formatter=SimpleDateFormat("mm:ss")
    fun getTime(time:Long):String{
        val cv=Calendar.getInstance()
        cv.timeInMillis=time
        return formatter.format(cv.time)
    }

}