package me.rei_m.kotlinsample.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.fragments.RxSampleFragment

public class RxSampleActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RxSampleActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, RxSampleFragment.newInstance())
                    .commit();
        }

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        })
    }
}
