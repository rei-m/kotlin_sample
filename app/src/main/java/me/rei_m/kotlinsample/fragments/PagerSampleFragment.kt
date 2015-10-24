package me.rei_m.kotlinsample.fragments

import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.rei_m.kotlinsample.R

public class PagerSampleFragment : AbstractFragment() {

    private var mPosition: Int? = null

    companion object {

        private val ARG_POSITION = "ARG_POSITION"

        fun newInstance(position: Int): PagerSampleFragment {
            val fragment = PagerSampleFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mPosition = arguments.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        val view = inflater.inflate(R.layout.fragment_pager_sample, container, false)

        (view.findViewById(R.id.text_position) as AppCompatTextView).text = mPosition.toString();

        (view.findViewById(R.id.button_pager_item) as AppCompatButton).setOnClickListener { v ->

        }

        return view
    }
}