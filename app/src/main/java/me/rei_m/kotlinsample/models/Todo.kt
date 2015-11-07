package me.rei_m.kotlinsample.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
public open class Todo(
        @PrimaryKey
        public open var id: Int = 0,
        public open var name: String = "",
        public open var isDone: Boolean = false
) : RealmObject() {}