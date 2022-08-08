package com.adedom.okrandroidtoflutter

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val methodChannel = FlutterEngineCache.getInstance()
            .get("my_engine_id")
            ?.dartExecutor
            ?.binaryMessenger
            ?.let { binaryMessenger ->
                MethodChannel(binaryMessenger, "com.choco/data")
            }

        methodChannel?.setMethodCallHandler { call, _ ->
            when (call.method) {
                "onResultToNative" -> {
                    val flutterResult = call.argument<String>("result")
                    findViewById<TextView>(R.id.tvResultFromFlutter).text = flutterResult
                }
                else -> {
                    Toast.makeText(baseContext, "Not implemented", Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<LinearLayout>(R.id.btSendDataToFlutter).setOnClickListener {
            val arguments = mapOf(
                "data" to findViewById<EditText>(R.id.etSendData).text.toString().trim()
            )
            methodChannel?.invokeMethod("openFlutterPage", arguments)
            val intent = FlutterActivity
                .withCachedEngine("my_engine_id")
                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                .build(this)
            startActivity(intent)
        }
    }
}