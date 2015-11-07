package me.rei_m.kotlinsample.fragments

import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.managers.TodoManager

/**
 * Created by rei_m on 2015/11/05.
 */
public class RealmSampleInputFragment private constructor() : AbstractFragment() {
    companion object {
        public fun newInstance(): RealmSampleInputFragment {
            val fragment = RealmSampleInputFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_realm_sample_input, container, false)

        val editTodo = view.findViewById(R.id.edit_todo_name) as AppCompatEditText

        view.findViewById(R.id.button_register_todo).setOnClickListener({ v ->
            TodoManager.createUser(activity.applicationContext, editTodo.text.toString())
        })

        return view
    }
}