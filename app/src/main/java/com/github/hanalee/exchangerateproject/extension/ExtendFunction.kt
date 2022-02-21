package com.github.hanalee.exchangerateproject.extension

import android.content.Context
import android.view.View
import android.widget.Toast


fun Context.toToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


