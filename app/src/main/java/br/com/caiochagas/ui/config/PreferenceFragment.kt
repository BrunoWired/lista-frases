package br.com.caiochagas.ui.config

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import br.com.caiochagas.R

class PreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}