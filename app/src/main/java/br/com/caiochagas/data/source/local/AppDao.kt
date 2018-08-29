package br.com.caiochagas.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import br.com.caiochagas.data.model.Message

@Dao
interface AppDao {

    @Query("SELECT * FROM message ORDER BY saved DESC, id DESC")
    fun getAll(): LiveData<List<Message>>

    @Query("SELECT * FROM message WHERE id = :id")
    fun getById(id: Int): LiveData<Message>

    @Query("SELECT id FROM message ORDER BY RANDOM() LIMIT 1")
    fun getRandom(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(message: List<Message>)

    @Update
    fun update(message: Message)
}