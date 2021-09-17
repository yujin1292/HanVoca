package com.example.hanvoca

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_show_blinker_mode.*
import kotlin.concurrent.timer

class ShowBlinkerModeActivity : AppCompatActivity() {

    var index: Int = 0
    var VocaName: String = " "
    var numOfWords: Int = 0
    var wordlist: List<WordDB> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_blinker_mode)

        VocaName = intent.getStringExtra("VocaName").toString()
        index = intent.getIntExtra("index", 0)
        numOfWords = intent.getIntExtra("numOfWords", 0)
        textView2.text = VocaName

        val realm = Realm.getDefaultInstance()
        val realmResults = realm.where<WordDB>().equalTo("voca", VocaName).findAll()

        wordlist = realmResults.toList()

        showBlinkerMode();

        exitbutton.setOnClickListener() {
            finish()
        }
    }

    private fun showBlinkerMode() {
        timer(period = 2000) {
            runOnUiThread {
                var wordtext = findViewById<TextView>(R.id.wordTextView2)
                var meantext = findViewById<TextView>(R.id.meanTextView2)

                if (index < (numOfWords)) {
                    wordtext.text = wordlist[index].word
                    meantext.text = wordlist[index].mean
                    index++
                } else {
                    index = 0
                }
            }
        }
    }

}