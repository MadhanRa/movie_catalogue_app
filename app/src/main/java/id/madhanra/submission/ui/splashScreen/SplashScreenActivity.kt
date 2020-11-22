package id.madhanra.submission.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import id.madhanra.submission.R
import id.madhanra.submission.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val imgAnim = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        val textAnim = AnimationUtils.loadAnimation(this, R.anim.text_animation)

        img_logo.animation = imgAnim
        img_text_logo.animation = textAnim

        handler = Handler()
        handler.postDelayed({
            val intent = Intent (this@SplashScreenActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}