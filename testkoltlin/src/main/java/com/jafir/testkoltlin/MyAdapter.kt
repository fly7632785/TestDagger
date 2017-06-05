package com.jafir.testkoltlin

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.nekocode.kotgo.component.rx.RxBus
import com.jafir.testkoltlin.event.ClickEvent
import com.jafir.testkoltlin.kotterknife.bindView
import kotlinx.android.synthetic.main.item_text.view.*

/**
 * Created by jafir on 2017/4/13.
 */
class MyAdapter(var data: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var onItemClickListener: ((Int) -> Unit)? = null
    var onItemLongClickListener: ((Int) -> Boolean)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var text: TextView = holder.text
        text.text = "test"
        holder.itemView.setOnClickListener {
            RxBus.send(ClickEvent())
            onItemClickListener?.invoke(position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(position) as Boolean
        }
        holder.setData()

    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent?.context, R.layout.item_text, null))
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val text by bindView<TextView>(R.id.text)
        fun setData() {
            itemView.text.text = "test"
            itemView.img.setImageResource(R.mipmap.ic_launcher)

        }


    }


}