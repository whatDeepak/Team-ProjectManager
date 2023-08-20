package com.vyarth.team

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // This is used to get the file from the assets folder and set it to the title textView.

        val typeface: Typeface = Typeface.createFromAsset(assets, "carbon bl.ttf")
        val tvAppName=findViewById<TextView>(R.id.tv_app_name)
        tvAppName.typeface = typeface

        // Adding the handler to after the a task after some delay.
        Handler().postDelayed({
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }, 2500) // Here we pass the delay time in milliSeconds after which the splash activity will disappear.
    }
}