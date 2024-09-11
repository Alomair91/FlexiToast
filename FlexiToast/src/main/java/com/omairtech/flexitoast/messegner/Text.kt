package com.omairtech.flexitoast.messegner

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class Text : Parcelable {
    @Parcelize
    data class Char(val string: String) : Text()

    @Parcelize
    data class Res(@StringRes val id: Int) : Text()
}

fun String.asText(): Text = Text.Char(this)

fun Int.asText(): Text = Text.Res(this)

fun Text.value(context: Context) = when (this) {
    is Text.Char -> string
    is Text.Res -> context.getString(id)
}