package br.com.caiochagas.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.caiochagas.data.model.Message
import br.com.caiochagas.utils.Injection
import br.com.caiochagas.utils.ext.enqueue

class MessagesViewModel : ViewModel() {

    private val repository by lazy { Injection.repository() }

    fun getAll(): LiveData<List<Message>> {

        repository.getUpdated().enqueue({ response ->
            if (response.isSuccessful) response.body()?.let { repository.add(it) }
        }, {})

        return repository.getAll()
    }

    fun getRandom(): LiveData<Message> = repository.getRandom()

    fun save(message: Message) = repository.update(message.copy(saved = !message.saved))
}