package com.example.sample.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.R


class MudhalScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashScreenDuration = 2000L
        val logo : ImageView = findViewById(R.id.logo)
        val pulseAnimation  = AnimationUtils.loadAnimation(this, R.anim.pulse)
        logo.startAnimation(pulseAnimation)
        Handler().postDelayed({
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }, splashScreenDuration)

    }
}