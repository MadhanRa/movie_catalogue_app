package id.madhanra.submission.ui.splashScreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import id.madhanra.submission.R
import id.madhanra.submission.databinding.ActivitySplashScreenBinding
import id.madhanra.submission.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val imgAnim = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        val textAnim = AnimationUtils.loadAnimation(this, R.anim.text_animation)

        binding.imgLogo.animation = imgAnim
        binding.imgTextLogo.animation = textAnim

        handler = Handler(mainLooper)
        handler.postDelayed({
            val intent = Intent (this@SplashScreenActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
    
}