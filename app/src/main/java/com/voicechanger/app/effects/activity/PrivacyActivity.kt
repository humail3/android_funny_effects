package com.voicechanger.app.effects.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import com.voicechanger.app.effects.R

class PrivacyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)
        var privacyField:TextView = findViewById<TextView>(R.id.privacytext)
        resources.openRawResource(R.raw.privacy).bufferedReader().use {
            privacyField.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(it.readText(), Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(it.readText())
            }
            privacyField.movementMethod = LinkMovementMethod.getInstance()
        }



    }
}