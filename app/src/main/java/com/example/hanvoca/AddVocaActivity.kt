package com.example.hanvoca

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_add_voca.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class AddVocaActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance() //인스턴스 얻기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voca)

        addBtn.setOnClickListener {
            if (addVocaEditText.text.isEmpty()) {
                alert("단어장 이름을 입력하세요") { yesButton { } }.show()
            } else {
                var addvoca = addVocaEditText.text.toString()

                if (checkDuplicated(addvoca)) addVocabulary()
                else {
                    alert("이미 존재하는 단어장 이름입니다.") { yesButton {} }.show()
                }
            }
        }

        cancelBtn.setOnClickListener { finish() }
    }

    private fun addVocabulary() {
        realm.beginTransaction()
        val newVoca = realm.createObject<VocaDB>()
        newVoca.apply {
            name = addVocaEditText.text.toString()
            numOfWords = 0
        }
        realm.commitTransaction()
        finish()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() //인스턴스 해제
    }

    private fun checkDuplicated(newVoca: String): Boolean {
        var size = realm.where<VocaDB>().equalTo("name", newVoca).findAll().size
        return size == 0
    }
}
