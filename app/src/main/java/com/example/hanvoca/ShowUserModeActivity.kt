package com.example.hanvoca

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.hanvoca.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_show_user_mode.*

class ShowUserModeActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user_mode)

        actList.add(this)

        var wordlist :MutableList<WordDB> = ArrayList()

        var wordtext : TextView? = null
        var meantext : TextView? = null

        var VocaName = intent.getStringExtra("VocaName")
        var index = intent.getIntExtra("index", 0)
        var numOfWords = intent.getIntExtra("numOfWords", 0)
        textVieww.text = VocaName

        val realm = Realm.getDefaultInstance()
        val realmResults = realm.where<WordDB>().equalTo("voca", VocaName).findAll()

        if(realmResults != null){
            for(word in realmResults)
                wordlist.add(word)
        }

        var i : Int = 0

        for(word in wordlist){
            if(word.index == index){
                break
            }
            i++
        }

        wordtext = findViewById<TextView>(R.id.wordTextView)
        meantext = findViewById<TextView>(R.id.meanTextView)

        wordtext.text = wordlist.get(i).word
        meantext.text = wordlist.get(i).mean

        prevBtn.setOnClickListener(){
            i = setPrev(i,wordlist)
        }

        nextBtn.setOnClickListener(){view->
            i = setNext(i,wordlist,numOfWords)

        }

        exitBtn.setOnClickListener(){view->
            var intent = Intent(this, ShowVocaActivity::class.java)
            intent.putExtra("vocaname",VocaName)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
    }

    fun setPrev(i : Int, wordlist :MutableList<WordDB> ) : Int{

        var wordtext = findViewById<TextView>(R.id.wordTextView)
        var meantext = findViewById<TextView>(R.id.meanTextView)

        wordtext.text = wordlist.get(i).word
        meantext.text = wordlist.get(i).mean
        if(i>0){
            return i-1
        }
        return i
    }
    fun setNext(i : Int, wordlist :MutableList<WordDB>, numOfWords:Int ) : Int{

        var wordtext = findViewById<TextView>(R.id.wordTextView)
        var meantext = findViewById<TextView>(R.id.meanTextView)

        wordtext.text = wordlist.get(i).word
        meantext.text = wordlist.get(i).mean
        if(i<(numOfWords-1)){
            return i+1
        }
        return i
    }

}
