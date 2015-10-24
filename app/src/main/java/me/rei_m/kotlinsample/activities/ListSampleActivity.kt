package me.rei_m.kotlinsample.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.activity.*

import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.fragments.InputSearchWordFragment
import me.rei_m.kotlinsample.fragments.ListSampleFragment

class ListSampleActivity : AppCompatActivity(), InputSearchWordFragment.OnFragmentInteractionListener {

    companion object {
        fun createIntent(context: Context) : Intent {
            return  Intent(context, ListSampleActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, InputSearchWordFragment.newInstance())
                    .commit();
        }

        fab.setOnClickListener({ v ->
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        })
    }

    override fun onFragmentInteraction(wordForSearch: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ListSampleFragment.newInstance(wordForSearch))
                .addToBackStack(null)
                .commit();
    }


}
