package jp.techacademy.takumi.tomizawa.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import jp.techacademy.takumi.tomizawa.taskapp.databinding.ActivityMakecategoryBinding
import io.realm.kotlin.delete
import java.util.*

class Makecategory : AppCompatActivity() {

    private lateinit var binding: ActivityMakecategoryBinding
    private lateinit var realm: Realm
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makecategory)
        binding = ActivityMakecategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val categoryId = intent.getIntExtra(EXTRA_CATEGORY, -1)

        // Realmデータベースとの接続を開く
        val config = RealmConfiguration.create(schema = setOf(Category::class))
        realm = Realm.open(config)

        // カテゴリの取得・初期化
        initCategory(categoryId)

        // 表示テスト用のタスクを作成してRealmに登録
        binding.addButton.setOnClickListener {
            addCategory()
            finish()
        }


        binding.backButton.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Realmデータベースとの接続を閉じる
        realm.close()
    }

    private fun initCategory(categoryId: Int) {
        // 引数のtaskIdに合致するタスクを検索
        val findCategory = realm.query<Category>("id==$categoryId").first().find()

        if (findCategory == null) {
            // 新規作成の場合
            category = Category()
            category.id = -1
        }
    }


    private fun addCategory() {
        // テキストに入力されたカテゴリ名を取得する
        val newCategory = binding.categoryText.text.toString()

        category.id = (realm.query<Category>().max("id", Int::class).find() ?: -1) + 1
        // 画面項目の値で更新
        category.contents = newCategory

        // 登録処理
        realm.writeBlocking {
            copyToRealm(category)
        }
        Log.d("test7", category.id.toString())
    }

}
