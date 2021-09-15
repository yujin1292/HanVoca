package com.example.hanvoca

import android.app.Activity
import android.support.v7.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {
    var actList = ArrayList<Activity>()

    fun actFinish() {
        for (i in actList)
            i.finish()

        finish()
    }
}