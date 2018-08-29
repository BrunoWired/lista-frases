package br.com.caiochagas.ui.image

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import br.com.caiochagas.R
import br.com.caiochagas.data.model.Message
import br.com.caiochagas.utils.Constants.MESSAGE
import br.com.caiochagas.utils.Visual
import br.com.caiochagas.utils.ext.rateMessage
import br.com.caiochagas.utils.ext.toast
import kotlinx.android.synthetic.main.activity_image.*
import java.io.File
import java.io.FileOutputStream

class ImageActivity : AppCompatActivity() {

    private val message by lazy { intent.getSerializableExtra(MESSAGE) as Message }

    private var showRateMessage = false

    private var editColorClicks = 0

    private var editSizeClicks = 0

    private var editFontClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image)

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        if (showRateMessage) rateMessage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults.first() == PERMISSION_GRANTED) generateImageFromView()
    }

    private fun setupViews() {

        img_message.text = message.text

        img.setBackgroundResource(Visual.colors[0])

        btn_color.setOnClickListener {
            editColorClicks++
            val i = Visual.colors[editColorClicks % Visual.colors.size]
            img.setBackgroundResource(i)
        }

        btn_size.setOnClickListener {
            editSizeClicks++
            val i = Visual.sizes[editSizeClicks % Visual.sizes.size]
            img_message.setTextSize(TypedValue.COMPLEX_UNIT_SP, i)
        }

        btn_font.setOnClickListener {
            editFontClicks++
            val i = Visual.fonts[editFontClicks % Visual.fonts.size]
            img_message.typeface = ResourcesCompat.getFont(this, i)
        }

        btn_share.setOnClickListener {
            if (!hasPermissionToWrite()) ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 1234)
            else generateImageFromView()
        }
    }

    private fun hasPermissionToWrite() = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED

    private fun generateImageFromView() {

        AsyncTask.execute {
            try {
                val file = getFile(img)
                runOnUiThread { share(file) }
            } catch (e: Exception) {
                runOnUiThread { toast("Erro: ${e.message}") }
            }
        }
    }

    private fun getFile(view: View): File {

        val dir = File(Environment.getExternalStorageDirectory(), "Frases").apply { if (!exists()) mkdir() }

        val file = File(dir, "${System.currentTimeMillis()}.png")

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

        view.draw(Canvas(bitmap))

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))

        return file
    }

    private fun share(file: File) {

        showRateMessage = true

        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)

        val intent = Intent(ACTION_SEND).apply {
            addFlags(FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(EXTRA_STREAM, uri)
            type = "image/png"
        }

        startActivity(Intent.createChooser(intent, getString(R.string.txt_share)))
    }
}
