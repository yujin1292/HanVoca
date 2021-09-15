package com.example.hanvoca

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.hanvoca.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_show_blinker_mode.*
import kotlin.concurrent.timer

class ShowBlinkerModeActivity : BaseActivity() {

    var index: Int = 0
    var VocaName: String = " "
    var numOfWords: Int = 0
    var wordlist: List<WordDB> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_blinker_mode)

        actList.add(this)

        VocaName = intent.getStringExtra("VocaName")
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

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
    }
}