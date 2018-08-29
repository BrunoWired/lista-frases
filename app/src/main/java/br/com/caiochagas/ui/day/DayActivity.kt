package br.com.caiochagas.ui.day

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import br.com.caiochagas.R
import br.com.caiochagas.data.model.Message
import br.com.caiochagas.ui.edit.EditActivity
import br.com.caiochagas.utils.Constants
import br.com.caiochagas.utils.ext.launch
import br.com.caiochagas.vm.MessagesViewModel
import kotlinx.android.synthetic.main.activity_day.*

class DayActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MessagesViewModel::class.java) }

    private var message: Message? = null

    private var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_day)

        setupViews()

        viewModel.getRandom().observe(this, Observer {
            message = it
            message?.let {
                day_message.text = it.text
                updateMenuIcon(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_day, menu)

        menuItem = menu.findItem(R.id.menu_save)

        message?.let { updateMenuIcon(it) }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_save) message?.let { viewModel.save(it) }

        return super.onOptionsItemSelected(item)
    }

    private fun setupViews() {

        btn_share.setOnClickListener { message?.let { launch<EditActivity> { putExtra(Constants.MESSAGE, it) } } }

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(day_message, 18, 26, 2, TypedValue.COMPLEX_UNIT_SP)
    }

    private fun updateMenuIcon(it: Message) = menuItem?.setIcon(if (it.saved) R.drawable.ic_save_white_filled else R.drawable.ic_save_white)
}