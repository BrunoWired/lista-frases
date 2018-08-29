package br.com.caiochagas.ui.main

import android.support.v7.util.DiffUtil
import br.com.caiochagas.data.model.Message

class MessagesDiff : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message?, newItem: Message?) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Message?, newItem: Message?) = oldItem == newItem

}