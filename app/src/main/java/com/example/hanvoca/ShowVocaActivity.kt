package com.example.hanvoca

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_show_voca.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class ShowVocaActivity : BaseActivity() {

    val realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_voca)
        actList.add(this)

        val vocaName = intent.getStringExtra("vocaname")
        var realmResults: RealmResults<WordDB> = getVocaList(vocaName)
        val wordListAdapter = WordListAdapter(realmResults)
        WordList.adapter = wordListAdapter
        var numOfWords = WordList.count

        Engtitle.text = vocaName
        WordList.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->

                var wordInfo = wordListAdapter.getItem(position)
                var index = wordInfo?.index
                var intent = Intent(this, SelectModeActivity::class.java)
                intent.putExtra("VocaName", vocaName)
                intent.putExtra("numOfWords", numOfWords)
                intent.putExtra("index", index)
                startActivity(intent)
            }

        WordList.onItemLongClickListener = AdapterView.OnItemLongClickListener{ _,_,_,_ ->
            var intent = Intent(this, DelWordActivity::class.java)
            intent.putExtra("vocaname", vocaName)
            startActivity(intent)

            true
        }

        floatingAddActionButton.setOnClickListener {
            val intent = Intent(this, AddWordActivity::class.java)
            intent.putExtra("vocaname", vocaName)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        actFinish()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        actList.remove(this)
    }

    private fun getVocaList(vocaName: String): RealmResults<WordDB> {
        return realm.where<WordDB>().equalTo("voca", vocaName).findAll()
    }
}
