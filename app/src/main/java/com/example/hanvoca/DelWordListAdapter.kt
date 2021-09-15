package com.example.hanvoca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class DelWordListAdapter (realmCollection: OrderedRealmCollection<WordDB>): RealmBaseAdapter<WordDB>(realmCollection){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val vh: DwViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.checkword, parent, false)
            vh = DwViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as DwViewHolder
        }

        if (adapterData != null) {
            val item = adapterData!![position]
            vh.wordTextView.text = item.word
            vh.meanTextView.text = item.mean
        }

        return view
    }

    class DwViewHolder(view: View){
        val wordTextView: TextView = view.findViewById(R.id.DelEng2)
        val meanTextView: TextView = view.findViewById(R.id.Kor2)
    }
}