package me.rei_m.kotlinsample.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.View
import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.fragments.RealmSampleInputFragment

/**
 * Created by rei_m on 2015/11/05.
 */
public class RealmSampleActivity : AbstractActivity() {

    companion object {
        public fun createIntent(context: Context): Intent {
            return Intent(context, RealmSampleActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, RealmSampleInputFragment.newInstance())
                    .commit();
        }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        })
    }
}
