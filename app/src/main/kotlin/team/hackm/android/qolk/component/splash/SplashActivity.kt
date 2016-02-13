package team.hackm.android.qolk.component.splash

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Display
import android.view.ViewGroup
import android.view.WindowManager
import butterknife.bindView
import com.gelitenight.waveview.library.WaveView
import team.hackm.android.qolk.R
import team.hackm.android.qolk.component.home.HomeActivity
import timber.log.Timber
import kotlin.properties.Delegates

/**
 * Created by shunhosaka on 2016/02/14.
 */
public class SplashActivity : AppCompatActivity(), WaveHelper.WaveAnimationEventListener {

    val waveView: WaveView by bindView(R.id.splash_waveview)
    var waveHelper: WaveHelper by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Set WaveView
        waveHelper = WaveHelper(waveView, SplashActivity@this)
        ensureWaveView()
    }

    override fun onResume() {
        super.onResume()
        waveHelper.start()
    }

    override fun onPause() {
        super.onPause()
        waveHelper.cancel()
    }

    private fun ensureWaveView() {
        val size: Point = getDisplaySize()
        val layoutParams: ViewGroup.LayoutParams = waveView.layoutParams
        layoutParams.height = size.y
        waveView.layoutParams = layoutParams
        waveView.setShapeType(WaveView.ShapeType.SQUARE)
        waveView.setWaveColor(
                ContextCompat.getColor(this, R.color.accent_alpha),
                ContextCompat.getColor(this, R.color.accent),
                false
        )
    }

    private fun getDisplaySize(): Point {
        val windowManager: WindowManager = getSystemService(WINDOW_SERVICE) as (WindowManager)
        val display: Display = windowManager.defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        return size
    }

    override fun onVerticalAnimationEnd() {
        Timber.d("onVerticalAnimationEnd")
        startActivity(HomeActivity.createIntent(this))
        finish()
    }

}