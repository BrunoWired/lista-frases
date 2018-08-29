package br.com.caiochagas.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.os.AsyncTask
import br.com.caiochagas.BuildConfig
import br.com.caiochagas.data.model.Message
import br.com.caiochagas.data.source.local.AppDao
import br.com.caiochagas.data.source.remote.ApiService

class Repository(private val dao: AppDao, private val api: ApiService) {

    fun getAll() = dao.getAll()

    fun getUpdated() = api.getAll()

    fun getRandom(): LiveData<Message> {

        val id = MutableLiveData<Int>()

        AsyncTask.execute { id.postValue(dao.getRandom()) }

        return Transformations.switchMap(id) { input -> dao.getById(input) }
    }

    fun add(list: List<Message>) = AsyncTask.execute { dao.add(list) }

    fun update(message: Message) = AsyncTask.execute { dao.update(message) }
}