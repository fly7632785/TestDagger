package com.jafir.testkoltlin.utils

import android.text.TextUtils

/**
 * Created by jafir on 2017/5/23.
 */

class StringUtils {
    companion object {
        @JvmStatic fun isEmpty(s: String): Boolean {
            return TextUtils.isEmpty(s)
        }

        fun isEmpty2() {

        }
    }
}