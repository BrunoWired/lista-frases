package br.com.caiochagas.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.caiochagas.data.model.Message

@Database(entities = [Message::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao
}