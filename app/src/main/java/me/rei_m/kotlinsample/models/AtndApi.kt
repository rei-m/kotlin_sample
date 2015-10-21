package me.rei_m.kotlinsample.models

import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import rx.Observable
import rx.Subscriber
import java.net.HttpURLConnection
import java.util.*

class AtndApi {

    companion object {

        public class Entity constructor(public val id: Int, public val title: String){

        }

        public fun request(): Observable<ArrayList<Entity>> {

            return Observable.create(object : Observable.OnSubscribe<ArrayList<Entity>> {

                override fun call(t: Subscriber<in ArrayList<Entity>>?) {

                    val request = Request
                            .Builder()
                            .url("https://api.atnd.org/events/?keyword_or=google,cloud&format=json")
                            .build()

                    val response = OkHttpClient().newCall(request).execute()

                    if (response.code() == HttpURLConnection.HTTP_OK) {

                        val responseBody = response.body().string()

                        val json = JSONObject(responseBody)

                        val eventList = ArrayList<Entity>()

                        val eventCount = json.getInt("results_returned")

                        if(0 < eventCount){
                            val events = json.getJSONArray("events")
                            for (i in 0..eventCount - 1) {
                                val e = events.getJSONObject(i).getJSONObject("event");
                                eventList.add(Entity(e.getString("event_id").toInt(), e.getString("title")))
                            }
                        }

                        t?.onNext(eventList)

                    } else {
                        t?.onError(Throwable("hoge"))
                    }

                    t?.onCompleted()
                }

            })
        }
    }
}