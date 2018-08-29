package br.com.caiochagas.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import br.com.caiochagas.R
import br.com.caiochagas.ui.config.ConfigActivity
import br.com.caiochagas.ui.edit.EditActivity
import br.com.caiochagas.utils.Constants.MESSAGE
import br.com.caiochagas.utils.ext.email
import br.com.caiochagas.utils.ext.isNullOrEmpty
import br.com.caiochagas.utils.ext.launch
import br.com.caiochagas.utils.ext.visibleIf
import br.com.caiochagas.vm.MessagesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MessagesViewModel::class.java) }

    private val adapter = MessagesAdapter { message, clickType ->

        when (clickType) {
            ClickType.SAVE -> viewModel.save(message).run { if (!message.saved) fab.show() }
            ClickType.USE -> launch<EditActivity> { putExtra(MESSAGE, message) }
        }
    }

    private val llm by lazy { rv.layoutManager as LinearLayoutManager }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupViews()

        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_notification -> launch<ConfigActivity>()
            R.id.menu_contact -> email(getString(R.string.app_name))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun observe() {

        viewModel.getAll().observe(this, Observer {
            pb.visibleIf(it.isNullOrEmpty())
            adapter.submitList(it)
        })
    }

    private fun setupViews() {

        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (llm.itemCount - llm.findLastVisibleItemPosition() < 2) fab.hide()
                else if (dy != 0) if (dy > 0) fab.show() else fab.hide()
            }
        })

        fab.setOnClickListener {
            rv.scrollToPosition(0)
            fab.hide()
        }
    }
}