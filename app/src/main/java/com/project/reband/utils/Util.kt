package com.project.reband.utils

import android.content.Context
import android.util.TypedValue
import com.project.reband.network.etc.EtcRepository

object Util {

    fun getInstrument(type: Int) : String {
        return when (type) {
            1 -> "기타"
            2 -> "드럼"
            3 -> "베이스"
            4 -> "보컬"
            else -> "키보드"
        }
    }

    fun getHashTag(hashTagList: List<Int>) : String {
        var hashTag = ""
        hashTagList.forEach {
            hashTag += "#${getInstrument(it)} "
        }
        return hashTag
    }

    fun dpToPx(context: Context, dp: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()
    }

}