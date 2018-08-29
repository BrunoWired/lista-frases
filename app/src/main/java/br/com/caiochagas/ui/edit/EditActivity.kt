package br.com.caiochagas.ui.edit

import android.content.ClipData
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.caiochagas.R
import br.com.caiochagas.data.model.Message
import br.com.caiochagas.ui.image.ImageActivity
import br.com.caiochagas.utils.Constants.MESSAGE
import br.com.caiochagas.utils.ext.getClipboardManager
import br.com.caiochagas.utils.ext.launch
import br.com.caiochagas.utils.ext.rateMessage
import br.com.caiochagas.utils.ext.value
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private val message by lazy { intent.getSerializableExtra(MESSAGE) as Message }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit)

        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    private fun setupViews() {

        edit_message.setText(message.text)

        btn_copy.setOnClickListener {

            getClipboardManager().primaryClip = ClipData.newPlainText("txt", edit_message.value)

            btn_copy.text = getString(R.string.txt_copied)

            Handler().postDelayed({ rateMessage() }, 500)
        }

        btn_image.setOnClickListener {

            launch<ImageActivity> { putExtra(MESSAGE, message.copy(text = edit_message.value)) }
        }
    }
}