package com.example.hanvoca

import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_del_voca.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class DelVocaActivity : AppCompatActivity() {

    var realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_del_voca)

        val realmResults = realm.where<VocaDB>().findAll()
        val rcvAdapter = DelVocaAdapter(realmResults)
        DelVocaList.adapter = rcvAdapter
        DelVocaList.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        delBtn.setOnClickListener {

            var checkeditems = getCheckedVoca()
            var cnt = rcvAdapter.count

            if (checkeditems.size() <= 0) {
                alert("삭제할 단어장을 선택해주세요") { yesButton {} }.show()
            } else {
                alert("정말 삭제하시겠습니까?") {
                    yesButton {
                        for (i in cnt - 1 downTo 0 step 1) {
                            if (checkeditems.get(i)) {
                                var delVocaInfo = rcvAdapter.getItem(i)
                                var delVocaName = delVocaInfo?.name
                                if (delVocaName != null)
                                    delVoca(delVocaName)
                            }
                        }

                        DelVocaList.clearChoices()
                        finish()
                    }
                    noButton { }
                }.show()
            }
        }

    }

    private fun delVoca(delVocaName: String) {
        realm.beginTransaction()
        val deleteItem = realm.where<VocaDB>().equalTo("name", delVocaName).findFirst()!!
        deleteItem.deleteFromRealm()
        val deleteItemInner = realm.where<WordDB>().equalTo("voca", delVocaName).findAll()!!
        deleteItemInner.deleteAllFromRealm()
        realm.commitTransaction()
    }

    private fun getCheckedVoca(): SparseBooleanArray {
        return DelVocaList.checkedItemPositions
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
