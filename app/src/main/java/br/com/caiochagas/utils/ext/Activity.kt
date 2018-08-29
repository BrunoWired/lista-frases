package br.com.caiochagas.utils.ext

import android.app.Activity
import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import br.com.caiochagas.utils.Constants.RATE

fun Activity.hideSystemUi() {
    window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
}

inline fun <reified T : Activity> Activity.launch(options: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(options))
}

fun Activity.toast(string: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, string, duration).show()
}

inline fun Activity.alert(body: AlertDialog.Builder.() -> AlertDialog.Builder) {
    if (!isFinishing) AlertDialog.Builder(this).body().show()
}

fun Activity.openUrl(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

fun Activity.openGooglePlay() = openUrl(googlePlayLink)

val Activity.googlePlayLink get() = "https://play.google.com/store/apps/details?id=$packageName"

fun Activity.rateMessage() {

    val pref = getSharedPreferences(RATE, MODE_PRIVATE)

    if (pref.getBoolean(RATE, true)) {

        alert {
            setCancelable(false)
            setTitle("Gostou do app?")
            setMessage("Você ajudaria muito avaliando o app com ★★★★★ no Google Play e deixando uma resenha curta.")
            setNeutralButton("Depois", null)
            setPositiveButton("Avaliar agora") { _, _ ->
                pref.edit { putBoolean(RATE, false) }
                openGooglePlay()
            }
        }
    }
}

fun Activity.email(subject: String) {

    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:frases@brunosemfio.com.br")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        if (resolveActivity(packageManager) != null) startActivity(this)
    }
}