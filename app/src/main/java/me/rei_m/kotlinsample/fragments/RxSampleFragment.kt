package me.rei_m.kotlinsample.fragments

import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jakewharton.rxbinding.widget.RxTextView

import rx.Observable

import me.rei_m.kotlinsample.R

public class RxSampleFragment : AbstractFragment() {

    companion object {
        fun newInstance(): RxSampleFragment {
            val fragment = RxSampleFragment()
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

        val view = inflater.inflate(R.layout.fragment_rx_sample, container, false)

        // 各入力欄のObservableを取得
        val orderName = RxTextView.textChanges(view.findViewById(R.id.edit_order_name) as AppCompatEditText)
        val orderPostalCode = RxTextView.textChanges(view.findViewById(R.id.edit_order_postal_code) as AppCompatEditText)
        val orderAddressFirst = RxTextView.textChanges(view.findViewById(R.id.edit_order_address_first) as AppCompatEditText)
        val orderAddressSecond = RxTextView.textChanges(view.findViewById(R.id.edit_order_address_second) as AppCompatEditText)

        // Buttonを取得
        val submitButton = view.findViewById(R.id.button_rx_submit) as AppCompatButton
        submitButton.isEnabled = false

        // ObservableをcombineLatestに食わして各項目のどれかに変更があった場合はStreamが流れるようにする
        // 引数の最後のAction1で流れてきた値をチェックしてすべて入力済ならtrueを流す
        Observable.combineLatest(orderName,
                orderPostalCode,
                orderAddressFirst,
                orderAddressSecond,
                { v1, v2, v3, v4 -> (0 < v1.length && 0 < v2.length && 0 < v3.length && 0 < v4.length)
        }).subscribe({isValid ->
            // 流れてきたStreamに従いボタンの有効/無効を切り替える
            submitButton.isEnabled = isValid
        })

        return view
    }
}
