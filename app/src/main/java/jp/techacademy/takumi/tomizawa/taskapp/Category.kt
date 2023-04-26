package jp.techacademy.takumi.tomizawa.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.io.Serializable

open class Category : RealmObject, Serializable {
    @PrimaryKey
    var id = 0
    var contents = "" // カテゴリ
}