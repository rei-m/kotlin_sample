package me.rei_m.kotlinsample.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View

import kotlinx.android.synthetic.activity_pager.*

import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.views.adapters.SamplePagerAdaptor

public class PagerSampleActivity : AbstractActivity() {

    companion object {
        public fun createIntent(context: Context) : Intent {
            return Intent(context, PagerSampleActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        setSupportActionBar(toolbar)

        pager.adapter = SamplePagerAdaptor(supportFragmentManager)

        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        })
    }

}
