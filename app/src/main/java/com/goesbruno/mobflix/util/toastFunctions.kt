package com.goesbruno.mobflix.util

import android.content.Context
import android.widget.Toast

fun makeToast(context: Context, error: String){
    return Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
}