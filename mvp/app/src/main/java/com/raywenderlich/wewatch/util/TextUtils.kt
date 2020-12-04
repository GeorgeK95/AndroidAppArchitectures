package com.raywenderlich.wewatch.util

import android.support.annotation.Nullable

object TextUtils {

    fun isEmpty(@Nullable str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }
}