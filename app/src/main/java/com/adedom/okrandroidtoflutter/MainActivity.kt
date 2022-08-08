package com.adedom.okrandroidtoflutter

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<LinearLayout>(R.id.btSendDataToFlutter).setOnClickListener {
            startActivity(FlutterActivity.createDefaultIntent(this))
        }
    }
}