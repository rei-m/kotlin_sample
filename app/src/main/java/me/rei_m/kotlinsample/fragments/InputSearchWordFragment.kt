package me.rei_m.kotlinsample.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.rei_m.kotlinsample.R

public class InputSearchWordFragment private constructor() : AbstractFragment(),
        View.OnClickListener {

    private var mListener: OnFragmentInteractionListener? = null

    companion object {

        public fun newInstance(): InputSearchWordFragment {
            val fragment = InputSearchWordFragment()
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

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_input_search_word, container, false)

        view.findViewById(R.id.button_search_atnd).setOnClickListener(this)

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mListener = activity as OnFragmentInteractionListener;
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_search_atnd ->
                onClickSearchAtnd((view.findViewById(R.id.edit_word_for_search) as AppCompatEditText).text.toString())
            else -> {}
        }
    }

    private fun onClickSearchAtnd(wordForSearch: String) {
        // TODO 値チェック入れる
        mListener?.onFragmentInteraction(wordForSearch)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(wordForSearch: String)
    }
}
