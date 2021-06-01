package ir.avabot.browser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import ir.avabot.browser.databinding.HomeBinding

// adb connect 192.168.1.4:

class Home : AppCompatActivity() {
    //val tts = TextToSpeech(applicationContext, null, "ir.avabot.tts")
    //tts.speak("سلام", TextToSpeech.QUEUE_ADD, null, "res")
    private lateinit var b: HomeBinding
    private lateinit var m: Model

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = HomeBinding.inflate(layoutInflater)
        m = ViewModelProvider(this, Model.Factory()).get("Model", Model::class.java)
        setContentView(b.root)
        Fun.init(this)

        b.web.apply {
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = false
                cacheMode = WebSettings.LOAD_DEFAULT
                setSupportZoom(true)
            }
            webViewClient = object : WebViewClient() {
                //override fun on
            }
        }
        b.search.setOnEditorActionListener { v, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_GO -> {
                    b.web.loadUrl(v.text.toString())
                    v.clearFocus()
                    true
                }
                else -> false
            }
        }
        b.search.requestFocus()
    }
}
