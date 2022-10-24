package com.dev_musashi.weather.util

fun getBaseTime(h: String, m: String): String {
    val result = if (m.toInt() < 45) {
        if (h == "00") "2330"
        else {
            val resultH = h.toInt() - 1
            if (resultH < 10) "0" + resultH + "30"
            else resultH.toString() + "30"
        }
    } else h + "30"

    return result
}

fun getTime(h: String, m: String): String {
    val hInt = h.toInt()
    val mInt = m.toInt()
    val baseTime: String =
        when (hInt) {
            in 2..4 -> if (mInt < 10) "2300" else "0200"
            in 5..7 -> if (mInt < 10) "0200" else "0500"
            in 8..10 -> if (mInt < 10) "0500" else "0800"
            in 11..13 -> if (mInt < 10) "0800" else "1100"
            in 14..16 -> if (mInt < 10) "1100" else "1400"
            in 17..19 -> if (mInt < 10) "1400" else "1700"
            in 20..22 -> if (mInt < 10) "1700" else "2000"
            else -> if (mInt < 10) "2000" else "2300"
        }
    return baseTime
}

