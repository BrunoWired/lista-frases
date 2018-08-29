package br.com.caiochagas.ui.main

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.ViewGroup
import br.com.caiochagas.R
import br.com.caiochagas.data.model.Message
import br.com.caiochagas.utils.ext.inflate

class MessagesAdapter(private val callback: (Message, ClickType) -> Unit) : ListAdapter<Message, MessagesViewHolder>(MessagesDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessagesViewHolder(parent.inflate(if (viewType == 1) R.layout.item_saved else R.layout.item))

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) = holder.bind(getItem(position), callback)

    override fun getItemViewType(position: Int) = if (getItem(position).saved) 1 else 0
}