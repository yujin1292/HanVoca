package com.example.hanvoca

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.random.Random

class TestActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance()
    var score: Int = 0
    var random: Int = 0
    var index = 1

    private val selected = mutableSetOf<Int>()
    var testWordList = listOf<WordDB>()
    var wrongWordList = arrayListOf<String>()
    var numOfWords = 0
    lateinit var inputWord : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        val vocaName = intent.getStringExtra("vocaname").let{ it ?: ""}
        numOfWords = intent.getIntExtra("numofwords", 0) // 단어장 단어개수
        val numOfQuiz = intent.getIntExtra("numofquiz", 0) //퀴즈 개수

        // 해당 단어장의 단어들 가져와서
        testWordList = getTestWordList(vocaName)

        inputWord  = findViewById<EditText>(R.id.inputWord)

        setWord()

        //입력할 영단어 초기화
        nextBtn.setOnClickListener {
            if (numOfQuiz > index) {
                checkAnswer(testWordList[random].word, inputWord.text.toString())
                index += 1
                setWord()
            } else {
                checkAnswer(testWordList[random].word, inputWord.text.toString())
                // 다끝났으면 결과화면으로 갑니다
                Log.d("TAG",score.toString())
                Log.d("TAG",wrongWordList.toString())

                var intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("numofquiz", numOfQuiz)
                intent.putExtra("score", score)
                intent.putStringArrayListExtra("list", wrongWordList as ArrayList<String>?)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setWord() {
        random = makeRandomIndex(numOfWords)
        Log.d("TAG", testWordList[random].toString())
        mean.text = testWordList[random].mean // 세팅
        inputWord.text?.clear()
    }

    private fun getTestWordList(vocaname: String): List<WordDB> {
        val realmResults = realm.where<WordDB>().equalTo("voca", vocaname).findAll()
        return realmResults.toList()
    }

    private fun checkAnswer(word: String, temp: String) {
        if (word.equals(temp, true)) score += 1
        else wrongWordList.add(word)
    }

    private fun makeRandomIndex(numOfWords: Int): Int {
        var ran = Random.nextInt(numOfWords)
        while (selected.contains(ran)) {
            ran = Random.nextInt(numOfWords)
        }
        selected.add(ran)
        return ran
    }

}


