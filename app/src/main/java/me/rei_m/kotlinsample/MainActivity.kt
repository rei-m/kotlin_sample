package me.rei_m.kotlinsample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.activity_main.*
import kotlinx.android.synthetic.content_main.*
import me.rei_m.kotlinsample.activities.*

public class MainActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Extensionいけてる！
        hello.text = "Hello Kotlin !!"

        // ListView開く
        open_list.setOnClickListener({ v ->
            startActivity(ListSampleActivity.createIntent(this))
        })

        open_pager.setOnClickListener({ v ->
            startActivity(PagerSampleActivity.createIntent(this))
        })

        open_reactive.setOnClickListener({ v ->
            startActivity(RxSampleActivity.createIntent(this))
        })

        open_realm.setOnClickListener({ v ->
            startActivity(RealmSampleActivity.createIntent(this))
        })

        // FABイベント
        fab.setOnClickListener({ v ->
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        })

        /**
         * Kotlin 文法いろいろ試す
         */

        // valで作られた変数は再代入不可
        val hoge = "hoge!"
        // hoge = "huga" // これは怒られる

        // 型は指定することもできる
        val huga: String = "huga"

        println(hoge)
        println(huga)

        // 配列
        val asc = Array(5, { i -> (i * i).toString() })
        print(asc)
        // 出力結果は ["0", "1", "4", "9", "16"]

        // String
        val str1 = "リテラル"

        // """ で囲んで複数行書ける
        val str2 = """
  for (c in "foo")
    print(c)
"""
        // ""内で$を使うと変数を参照できる。
        val str3 = "${str1}の長さは${str1.length}"

        println(str1)
        println(str2)
        println(str3)

        // if式
        if (true) {
            println(1)
            println(2)
        } else {
            println(3)
        }

        // 三項演算子的に
        val ifif = if(true) "true" else "false"
        println(ifif)

        // when式
        val wval = 1;
        when (wval) {
            1 -> println("x == 1")
            2 -> println("x == 2")
            else -> { // Note the block
                println("x is neither 1 nor 2")
            }
        }

        // in で Rangeを使った評価ができる
        // !in は否定なのでRange外で真
        when (wval) {
            in 1..10 -> println("x is in the range")
            !in 10..20 -> println("x is outside the range")
            else -> println("none of the above")
        }

        // whenの結果を代入できる
        val retWhen = when (wval) {
            in 1..10 -> wval * 2
            else -> 0
        }

        println(retWhen)

        // if elseif else 的な
        when {
            wval < 10 -> println("10以下")
            wval < 20 -> println("20以下")
            else -> println("20より大きい")
        }

        // Loop
        for(item in "abcdefg") {
            println(item)
        }
        for(idx in "abcdefg".indices) {
            println(idx)
        }
        for((i, v) in "abcdefg".withIndex()) {
            println("$v は $i 番目")
        }

        // ラベルを指定してbreakするとそのループを抜ける
        loop@ for (i in 1..10) {
            println(i)
            for (j in 1..100) {
                if(5 < j) {
                    break@loop
                }
                println(j)
            }
        }

        /**
         * 出力結果は
         * 1
         * 1
         * 2
         * 3
         * 4
         * 5
         */

        // continueは指定したラベルのループは止まらずに回り続ける
        loop@ for (i in 1..10) {
            println(i)
            for (j in 1..100) {
                if(5 < j) {
                    continue@loop
                }
                println(j)
            }
        }

        val bar = foo()
        println(bar)

    }

    private fun foo() {
        val ints = 1..10
        ints.forEach(fun(value: Int) {
            if (value < 5) return
            print(value)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
