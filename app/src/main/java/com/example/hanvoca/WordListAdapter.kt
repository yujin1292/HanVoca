package com.example.hanvoca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class WordListAdapter(realmResult:OrderedRealmCollection<WordDB>):RealmBaseAdapter<WordDB>(realmResult){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: WordViewHolder
        val view:View

        if(convertView==null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.word2,parent,false)
            vh = WordViewHolder(view)
            view.tag = vh
        }else{
            view = convertView
            vh = view.tag as WordViewHolder
        }

        if(adapterData!=null){
            val item = adapterData!![position]
            vh.wordTextView.text = item.word
            vh.meanTextView.text = item.mean
        }

        return view

    }
    class WordViewHolder(view: View){
        val wordTextView:TextView = view.findViewById(R.id.Eng)
        val meanTextView:TextView = view.findViewById(R.id.Kor)
    }

}

