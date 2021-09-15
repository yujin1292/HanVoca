package com.example.hanvoca

import io.realm.Realm
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class TextReader {
    var realm: Realm = Realm.getDefaultInstance() //인스턴스 얻기
    private fun read(file: String) {
        try {
            val filename = "$file.txt"
            val `in` = BufferedReader(FileReader(filename))
            var s: String
            var split: Array<String?>
            var i = 0

            //단어장 생성
            realm.beginTransaction() //트랜잭션 시작
            val newVoca = realm.createObject(VocaDB::class.java) //새 객체 생성
            //값 설정
            newVoca.name = file
            newVoca.numOfWords = i
            realm.commitTransaction()
            while (`in`.readLine().also { s = it } != null) {
                split = s.split(" ").toTypedArray()
                realm.beginTransaction() //트랜잭션 시작
                val newWord = realm.createObject(WordDB::class.java) //새 객체 생성
                //값 설정
                newWord.word = split[0]!!
                newWord.mean = split[1]!!
                newWord.voca = file
                newWord.index = i
                realm.commitTransaction()
                i++
            }
            `in`.close()
        } catch (e: IOException) {
            System.err.println(e) // 에러가 있다면 메시지 출력
            System.exit(1)
        }
    }

    fun readAll() {
        val a = "DAY "
        var name: String
        for (i in 1..10) {
            name = a + i.toString()
            read(name)
        }
    }
}