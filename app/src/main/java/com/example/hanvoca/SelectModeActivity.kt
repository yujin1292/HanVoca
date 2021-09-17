package com.example.hanvoca

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_mode.*


class SelectModeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_mode)

        var VocaName = intent.getStringExtra("VocaName")
        var index = intent.getIntExtra("index", 0)
        var numOfWords = intent.getIntExtra("numOfWords", 0)

        userbtn.setOnClickListener {
            var intent = Intent(this, ShowUserModeActivity::class.java)
            intent.putExtra("VocaName", VocaName)
            intent.putExtra("index", index)
            intent.putExtra("numOfWords", numOfWords)
            startActivity(intent)
        }

        //깜빡이모드버튼 눌리면 전달받은 인자를 다시 전달하고 화면 넘길게용
        autobtn.setOnClickListener {
            var intent = Intent(this, ShowBlinkerModeActivity::class.java)
            intent.putExtra("VocaName", VocaName)
            intent.putExtra("index", index)
            intent.putExtra("numOfWords", numOfWords)
            startActivity(intent)
        }
    }

}
