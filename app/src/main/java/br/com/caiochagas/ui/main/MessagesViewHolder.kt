package br.com.caiochagas.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.caiochagas.data.model.Message
import kotlinx.android.synthetic.main.item.view.*

class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message, callback: (Message, ClickType) -> Unit) {

        itemView.item_text.text = message.text
        itemView.item_save.setOnClickListener { callback(message, ClickType.SAVE) }
        itemView.setOnClickListener { callback(message, ClickType.USE) }
    }
}