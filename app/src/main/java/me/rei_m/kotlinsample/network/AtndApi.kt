package me.rei_m.kotlinsample.network

import java.net.HttpURLConnection

import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request

import rx.Observable
import rx.Subscriber

public final class AtndApi private constructor() {

    companion object {

        public class Entity(val id: Int, val title: String)

        public fun request(wordForSearch: String, startIndex: Int): Observable<Entity> {

            return Observable.create(object : Observable.OnSubscribe<Entity> {

                override fun call(t: Subscriber<in Entity>) {

                    val request = Request.Builder()
                            .url("https://api.atnd.org/events/?format=json&keyword_or=$wordForSearch&start=$startIndex")
                            .build()

                    val response = OkHttpClient().newCall(request).execute()

                    if (response.code() == HttpURLConnection.HTTP_OK) {

                        val responseBody = response.body().string()

                        val json = JSONObject(responseBody)

                        val eventCount = json.getInt("results_returned")

                        if(0 < eventCount){
                            val events = json.getJSONArray("events")
                            for (i in 0..eventCount - 1) {
                                val e = events.getJSONObject(i).getJSONObject("event");
                                t.onNext(Entity(e.getString("event_id").toInt(), e.getString("title")))
                            }
                        }

                    } else {
                        t.onError(Throwable(response.code().toString()))
                    }

                    t.onCompleted()
                }

            })
        }
    }
}