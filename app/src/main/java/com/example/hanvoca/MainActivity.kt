package com.example.hanvoca

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.widget.AdapterView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.io.InputStream

class MainActivity : BaseActivity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actList.add(this)
        var load = true

        //모든 데이터 가져오기
        var realmResults = realm.where<VocaDB>().findAll()

        //비어있다면, 맨처음 실행시키는거니까 단어를 읽어와야함
        if (realmResults.isEmpty()) {
            alert("단어를 불러오시겠습니까?") {
                yesButton {
                    makeVocabulary()
                    realmResults = realm.where<VocaDB>().findAll() //다시 읽어옴
                }
                noButton { load = false }
            }.show()
        }

        val rcvAdapter = VocaListAdapter(realmResults)
        VocaList.adapter = rcvAdapter

        VocaList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ShowVocaActivity::class.java)
            var vocaInfo = rcvAdapter.getItem(position)
            var vocaname = vocaInfo?.name
            intent.putExtra("vocaname", vocaname)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        delVocaBtn.setOnClickListener {
            var vocalist = realm.where<VocaDB>().findAll()
            if (vocalist.size == 0) {
                alert("삭제할 단어장이 없습니다.") { yesButton { } }.show()
            } else {
                var intent = Intent(this, DelVocaActivity::class.java)
                startActivity(intent)
            }

        }

        addVocaBtn.setOnClickListener {
            var intent = Intent(this, AddVocaActivity::class.java)
            startActivity(intent)
        }

        testBtn.setOnClickListener {
            var intent = Intent(this, TestSelectVocaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun nextIndex(): Int {
        val maxId = realm.where<WordDB>().max("index")
        return if (maxId != null) maxId.toInt() + 1 else 0
    }

    private fun makeVocabulary() {

        for (day in 1..10) {
            val filename = "day$day"
            //단어장 생성
            realm.beginTransaction()
            realm.createObject(VocaDB::class.java).apply {
                name = filename
                numOfWords = 0
            }
            realm.commitTransaction()

            // Read
            val filenameText = "day$day.txt"
            val assetManager: AssetManager = resources.assets
            var inputStream: InputStream = assetManager.open(filenameText)
            val inputString: String =
                inputStream.bufferedReader().use { it.readText() }

            val lines = inputString.split("\n")
            for (line in lines) {
                val split = line.split("::")
                realm.beginTransaction()
                realm.createObject<WordDB>(nextIndex()).apply {
                    word = split[0]
                    mean = split[1]
                    voca = filename
                }
                realm.where<VocaDB>().equalTo("name", filename).findFirst()?.apply {
                    numOfWords += 1
                }
                realm.commitTransaction()
            }
        }
    }

}
