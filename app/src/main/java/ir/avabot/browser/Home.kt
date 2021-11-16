package ir.avabot.browser

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import ir.avabot.browser.Fun.Companion.vis
import ir.avabot.browser.databinding.HomeBinding

// adb connect 192.168.1.20:

class Home : AppCompatActivity() {
    //val tts = TextToSpeech(applicationContext, null, "ir.avabot.tts")
    //tts.speak("سلام", TextToSpeech.QUEUE_ADD, null, "res")
    private lateinit var b: HomeBinding
    private lateinit var m: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = HomeBinding.inflate(layoutInflater)
        m = ViewModelProvider(this, Model.Factory()).get("Model", Model::class.java)
        setContentView(b.root)
        Fun.init(this)

        b.web.apply {
            settings.apply {
                @Suppress("SetJavaScriptEnabled")
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = false
                cacheMode = WebSettings.LOAD_DEFAULT
                setSupportZoom(true)
            }
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    vis(b.progress)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    vis(b.progress, false)
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    (b.progress.layoutParams as ConstraintLayout.LayoutParams).apply {
                        matchConstraintPercentWidth = newProgress / 100f
                    }
                }
            }
            setBackgroundColor(0)
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
