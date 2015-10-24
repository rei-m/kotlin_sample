package me.rei_m.kotlinsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import rx.observers.Observers

import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.models.AtndApi

class ListSampleFragment : AbstractFragment(), AdapterView.OnItemClickListener {

    private var mWordForSearch: String = ""

    private val mSubscriptions = CompositeSubscription()

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private var mListView: AbsListView? = null
    private var mAdapter: ArrayAdapter<String>? = null

    companion object  {

        private val ARG_WORD_FOR_SEARCH = "wordForSearch"

        fun newInstance(wordForSearch: String) : ListSampleFragment {
            val fragment = ListSampleFragment()
            val args = Bundle()
            args.putString(ARG_WORD_FOR_SEARCH, wordForSearch)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mWordForSearch = arguments.getString(ARG_WORD_FOR_SEARCH)
        }

        // Adaptorを作成
        mAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_item, container, false)

        // ListViewにAdaptorとリスナをセット
        mListView = view.findViewById(R.id.list_event) as AbsListView
        mListView!!.adapter = mAdapter
        mListView!!.onItemClickListener = this

        // Observerを作成
        val observer = Observers.create<AtndApi.Companion.Entity>(
                { t ->
                    // onNext
                    mAdapter!!.add(t.title)
                },
                { e ->
                    // onError
                    println(e.message)
                },
                {
                    // onComplete
                    mAdapter!!.notifyDataSetChanged()
                })

        // Observableを作成
        val observable = AtndApi.request(mWordForSearch)

        // Observableにobserverを登録してsubscribeする
        val subscription = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

        // 購読を開始する
        mSubscriptions.add(subscription);

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // 購読を解除する
        mSubscriptions.unsubscribe()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }
}
