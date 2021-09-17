package com.example.hanvoca

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_test_set_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class TestSetDetailActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance() //인스턴스 얻기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_set_detail)

        val vocaName = intent.getStringExtra("vocaname")
        val numOfWords = intent.getIntExtra("numofwords", 0)

        startBtn.setOnClickListener {
            var inputNum: Int = inputnum.text.toString().toInt()
            if (numOfWords >= inputNum) {
                val intent = Intent(this, TestActivity::class.java)
                intent.putExtra("vocaname", vocaName)
                intent.putExtra("numofwords", numOfWords)
                intent.putExtra("numofquiz", inputNum)
                startActivity(intent)
                finish()
            } else {
                alert("단어장의 단어수보다 크게 입력하였습니다") { yesButton { } }.show()
            }
        }

    }
}
