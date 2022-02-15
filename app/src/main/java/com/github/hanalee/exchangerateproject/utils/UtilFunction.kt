package com.github.hanalee.exchangerateproject.utils

import okhttp3.logging.HttpLoggingInterceptor
import java.text.DecimalFormat
import java.util.*

class UtilFunction {
    companion object {

        fun httpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor { message -> print("okHttp : $message") }
            return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        fun getDateWithFormat(timeStamp: Int): String {
            val lmODate = Date(timeStamp * 1000L)
            val lmOGCal = GregorianCalendar()
            lmOGCal.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            lmOGCal.time = lmODate
            val year = lmOGCal[Calendar.YEAR].toString()
            var month = (lmOGCal[Calendar.MONTH] + 1).toString()
            var day = lmOGCal[Calendar.DATE].toString()
            var hour = lmOGCal[Calendar.HOUR_OF_DAY].toString()
            var minute = lmOGCal[Calendar.MINUTE].toString()

            if (month.length == 1) {
                month = "0$month"
            }
            if (day.length == 1) {
                day = "0$day"
            }
            if (hour.length == 1) {
                hour = "0$hour"
            }
            if (minute.length == 1) {
                minute = "0$minute"
            }

            return "$year-$month-$day $hour:$minute"
        }

        fun getMoneyWithFormat(money: Double): String {
            val decimalFormat = DecimalFormat("#,##0.00")
            return decimalFormat.format(money)
        }
    }
}