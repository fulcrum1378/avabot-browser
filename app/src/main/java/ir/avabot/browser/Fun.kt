package ir.avabot.browser

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Process
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.system.exitProcess

class Fun {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var c: Context
        lateinit var sp: SharedPreferences
        var dm = DisplayMetrics()
        var night = false


        fun init(that: AppCompatActivity) {
            c = that.applicationContext
            sp = that.getPreferences(Context.MODE_PRIVATE)
            dm = that.resources.displayMetrics
            night = c.resources.getBoolean(R.bool.night)
        }

        fun dp(px: Int) = (dm.density * px.toFloat()).toInt()

        fun now() = Calendar.getInstance().timeInMillis

        fun color(res: Int) = ContextCompat.getColor(c, res)

        fun pdcf(c: Context, res: Int) =
            PorterDuffColorFilter(ContextCompat.getColor(c, res), PorterDuff.Mode.SRC_IN)

        fun exit(that: AppCompatActivity) {
            that.moveTaskToBack(true)
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
    }
}