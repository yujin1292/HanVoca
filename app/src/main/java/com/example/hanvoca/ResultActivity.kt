package com.example.hanvoca

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance() //인스턴스 얻기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var score = intent.getIntExtra("score",0)
        var maxscore = intent.getIntExtra("numofquiz",0)
        scoreText.text = "$score / $maxscore"

        var wrongList = intent.getStringArrayListExtra("list")


        var ResultAdapter = ResultAdapter()
        for( item in wrongList){
            var temp = realm.where<WordDB>().equalTo("word",item).findFirst()
            ResultAdapter.addItem(temp?.word,temp?.mean)
        }
        wrongListView.adapter = ResultAdapter

        exitBtn.setOnClickListener{
            finish()
        }

    }
}