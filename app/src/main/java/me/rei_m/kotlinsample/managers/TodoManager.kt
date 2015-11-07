package me.rei_m.kotlinsample.managers

import android.content.Context
import io.realm.Realm
import me.rei_m.kotlinsample.models.Todo

public class TodoManager private constructor() {
    companion object {

        public fun createUser(context: Context, name: String) {
//            val realm = Realm.getInstance(context)
//            realm.beginTransaction()
            val todo = Todo(id = getNextPrimaryKey(context), name = name)
//            realm.copyToRealm(todo)
//            realm.commitTransaction()
        }

        private fun getNextPrimaryKey(context: Context): Int {
            val realm = Realm.getDefaultInstance()
            val query = realm.where(javaClass<Todo>()).findAll()
//            val latestTodo = query.findAll().max(Todo::id.name)
//            println(latestTodo)
//            val hoge = query.findAll()
//            return if(latestTodo == null) 1 else latestTodo.toInt() + 1
            return 1
        }
    }
}