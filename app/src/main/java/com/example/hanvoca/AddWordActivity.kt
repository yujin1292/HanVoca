package com.example.hanvoca

import android.os.Bundle
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_add_word.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class AddWordActivity : BaseActivity() {

    val realm: Realm = Realm.getDefaultInstance()

    var vocaName: String = " "
    var mean: String = " "
    var word: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        actList.add(this)

        vocaName = intent.getStringExtra("vocaname")
        var valid: Boolean = true

        word = wordEditText.text.toString()
        mean = meanEditText.text.toString()

        addWordBtn.setOnClickListener {
            word = wordEditText.text.toString()
            mean = meanEditText.text.toString()

            valid = word.all { it.isLetter() || it == ' ' || it == '-' }

            if (word.isEmpty()) {
                alert("단어를 입력해주세요") { yesButton { } }.show()
            } else if (mean.isEmpty()) {
                alert("뜻을 입력해주세요") { yesButton { } }.show()
            } else if (!valid) {
                alert("단어에는 영어, 공백, 특수기호 '-'만 입력할 수 있습니다.") { yesButton { } }.show()
            } else {
                if (checkSameWord(word)) {
                    alert("이미 존재하는 단어입니다.\n계속 진행하시겠습니까?") {
                        yesButton { addWord(vocaName, word, mean) }
                        noButton {}
                    }.show()
                } else addWord(vocaName, word, mean)
            }
        }
        cancelWordBtn.setOnClickListener { finish() }
    }

    private fun addWord(vocaName: String, word: String, mean: String) {

        realm.beginTransaction()

        val newWord = realm.createObject<WordDB>(nextIndex())
        newWord.apply {
            this.word = word
            this.mean = mean
            this.voca = vocaName
        }
        val updateVoca = realm.where<VocaDB>().equalTo("name", vocaName).findFirst()!!
        updateVoca.numOfWords = updateVoca.numOfWords + 1
        realm.commitTransaction() //트랜잭션 종료
        finish()
    }

    private fun nextIndex(): Int {
        val maxId = realm.where<WordDB>().max("index")
        if (maxId != null) return maxId.toInt() + 1
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
        realm.close()
    }

    private fun checkSameWord(word: String) = realm.where<WordDB>().equalTo("word", word).findAll().size > 0

}