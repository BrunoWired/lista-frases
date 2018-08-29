package br.com.caiochagas.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
data class Message(@PrimaryKey val id: Int, val text: String, val saved: Boolean) : Serializable