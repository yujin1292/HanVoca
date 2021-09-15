package com.example.hanvoca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class ResultAdapter : BaseAdapter() {

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
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val context = parent.context

        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.word2, parent, false)
        }

        val meanText = convertView?.findViewById<View>(R.id.Kor) as TextView
        val wordText = convertView?.findViewById<View>(R.id.Eng) as TextView

        val myItem = getItem(position)
        meanText.text = myItem.mean
        wordText.text = myItem.word

        return convertView!!
    }

    fun addItem(word: String?, mean: String?) {
        val mItem = WordDB()
        mItem.mean = mean!!
        mItem.word = word!!
        mItems.add(mItem)
    }
}