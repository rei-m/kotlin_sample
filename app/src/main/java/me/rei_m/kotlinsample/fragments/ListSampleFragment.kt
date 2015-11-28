package me.rei_m.kotlinsample.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout
import me.rei_m.kotlinsample.R
import me.rei_m.kotlinsample.network.AtndApi
import rx.android.schedulers.AndroidSchedulers
import rx.observers.Observers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

public class ListSampleFragment private constructor() : AbstractFragment(),
        AdapterView.OnItemClickListener {

    private var mWordForSearch: String = ""

    private val mSubscriptions = CompositeSubscription()

    private var mListView: AbsListView? = null
    private var mAdapter: ArrayAdapter<String>? = null

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
        mListView?.adapter = mAdapter
        mListView?.onItemClickListener = this

        // Observerを作成
        val observer = Observers.create<AtndApi.Companion.Entity>(
                { t ->
                    // onNext
                    mAdapter?.add(t.title)
                    mAdapter?.notifyDataSetChanged()
                },
                { e ->
                    // onError
                    println("Error!! ${e.message}")
                },
                {
                    // onComplete
                    mAdapter?.notifyDataSetChanged()
                })

        // Observableを作成
        // 配信時は新しいスレッド
        // 監視者はメインスレッド
        val observable = AtndApi.request(mWordForSearch, 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

        val refreshLayout = view.findViewById(R.id.refresh) as SwipeRefreshLayout

        // ローディング中のプログレスの色を設定
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.LTGRAY)

        val refreshLayoutRefreshing = RxSwipeRefreshLayout.refreshing(refreshLayout)
        val refreshLayoutStream = RxSwipeRefreshLayout.refreshes(refreshLayout)

        // 購読を開始

        // 初回のAPIコール
        mSubscriptions.add(observable.subscribe(observer))

        // RefreshLayoutを監視
        mSubscriptions.add(refreshLayoutStream.subscribe({

            // ローディングが始まったらAPIコール
            val subscription = AtndApi.request(mWordForSearch, mAdapter?.count!! + 1)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .finallyDo({
                        // 読み込みが終わったらRefreshLayoutのローディング状態を解除
                        refreshLayoutRefreshing.call(false)
                    })
                    .subscribe(observer)

            mSubscriptions.add(subscription)
        }))

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // 購読を解除する
        mSubscriptions.unsubscribe()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val refreshLayout = activity.findViewById(R.id.refresh) as SwipeRefreshLayout
        val refreshLayoutRefreshing = RxSwipeRefreshLayout.refreshing(refreshLayout)
        refreshLayoutRefreshing.call(true)
    }
}
