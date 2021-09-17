package com.example.hanvoca

import android.app.Application
import io.realm.Realm

class Hanvoca: Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}