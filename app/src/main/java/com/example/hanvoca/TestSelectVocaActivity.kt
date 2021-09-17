package com.example.hanvoca

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_test__select_voca.*

class TestSelectVocaActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance() //인스턴스 얻기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test__select_voca)

        var dbResults = realm.where<VocaDB>().findAll()
        val vocaAdapter = VocaListAdapter(dbResults)

        vocaList.adapter = vocaAdapter
        vocaList.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                var vocaInfo = vocaAdapter.getItem(position)
                var vocaname = vocaInfo?.name
                var numOfWords = vocaInfo?.numOfWords

                val intent = Intent(this, TestSetDetailActivity::class.java)
                intent.putExtra("vocaname", vocaname)
                intent.putExtra("numofwords", numOfWords)
                startActivity(intent)
                finish()
            }
    }
}

