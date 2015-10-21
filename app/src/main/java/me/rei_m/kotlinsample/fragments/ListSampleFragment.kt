package me.rei_m.kotlinsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter

import java.util.*

import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.models.AtndApi

class ListSampleFragment : BaseFragment(), AdapterView.OnItemClickListener {

    // TODO: Rename and change types of parameters
//    private var mParam1: String? = null
//    private var mParam2: String? = null

    private val subscriptions = CompositeSubscription();

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private var mListView: AdapterView<ListAdapter>? = null
    private var mAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
//            mParam1 = arguments.getString(ARG_PARAM1)
//            mParam2 = arguments.getString(ARG_PARAM2)
        }

        // TODO: Change Adapter to display your content
        mAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_item, container, false)

        // Set the adapter
        mListView = view.findViewById(R.id.list_event) as AbsListView
        (mListView as AdapterView<ListAdapter>).adapter = mAdapter
        (mListView as AdapterView<ListAdapter>).onItemClickListener = this

        subscriptions.add(AtndApi
                .request()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<AtndApi.Companion.Entity>> {
                    override fun onError(e: Throwable?) {
                        activity?.finish()
                    }

                    override fun onNext(t: ArrayList<AtndApi.Companion.Entity>?) {
                        t?.map { v -> mAdapter?.add(v.title) }
                    }

                    override fun onCompleted() {
                        mAdapter?.notifyDataSetChanged()
                    }
                })
        );

        return view
    }

    override fun onDestroyView() {
        subscriptions.unsubscribe();
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }

    companion object {

        private val ARG_WORD_FOR_SEARCH = "wordForSearch"

        fun newInstance(wordForSearch: String): ListSampleFragment {
            val fragment = ListSampleFragment()
            val args = Bundle()
            args.putString(ARG_WORD_FOR_SEARCH, wordForSearch)
            fragment.arguments = args
            return fragment
        }
    }

}
