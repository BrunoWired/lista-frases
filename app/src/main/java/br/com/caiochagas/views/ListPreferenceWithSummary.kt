package br.com.caiochagas.views

import android.content.Context
import android.support.v7.preference.ListPreference
import android.util.AttributeSet

class ListPreferenceWithSummary(context: Context, attrs: AttributeSet) : ListPreference(context, attrs) {

    override fun setValue(value: String) {
        super.setValue(value)
        summary = value
    }

    override fun setSummary(summary: CharSequence) {
        super.setSummary(entry)
    }
}