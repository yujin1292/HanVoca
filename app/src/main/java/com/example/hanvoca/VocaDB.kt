package com.example.hanvoca

import io.realm.RealmObject

open class VocaDB (
    var name:String=" ",
    var numOfWords:Int=0
):RealmObject()