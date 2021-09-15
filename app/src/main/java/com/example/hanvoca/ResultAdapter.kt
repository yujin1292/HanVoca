package com.example.hanvoca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class ResultAdapter : BaseAdapter() {
    /* 아이템을 세트로 담기 위한 어레이 */
    private val mItems = ArrayList<WordDB>()
    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(position: Int): WordDB {
        return mItems[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val context = parent.context

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.word2, parent, false)
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        val meanText = convertView.findViewById<View>(R.id.Kor) as TextView
        val wordText = convertView.findViewById<View>(R.id.Eng) as TextView


        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        val myItem = getItem(position)

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */meanText.text = myItem.mean
        wordText.text = myItem.word

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */return convertView
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    fun addItem(word: String?, mean: String?) {
        val mItem = WordDB()

        /* MyItem에 아이템을 setting한다. */mItem.mean = mean!!
        mItem.word = word!!
        /* mItems에 MyItem을 추가한다. */mItems.add(mItem)
    }
}