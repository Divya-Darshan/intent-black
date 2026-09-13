package com.darshnidev.black

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private lateinit var ageTextView: TextView
    private lateinit var hoursLivedTextView: TextView
    private val handler = Handler(Looper.getMainLooper())
    
    // Birth date: Feb 20, 2006
    private val birthTimeMillis: Long = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        set(2006, Calendar.FEBRUARY, 20, 0, 0, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private val updateRunnable = object : Runnable {
        override fun run() {
            updateAge()
            handler.postDelayed(this, 50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ageTextView = findViewById(R.id.age_text)
        hoursLivedTextView = findViewById(R.id.hours_lived_text)

        // Apply frosted glass effect for Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val capsuleContainer = findViewById<View>(R.id.capsule_container)
            val blurEffect = RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.CLAMP)
            // Note: In a real app, you'd apply blur to the view *behind* the translucent layer.
            // For now, we apply it to the container itself to simulate the 'frosted' look.
            capsuleContainer.setRenderEffect(blurEffect)
        }

        handler.post(updateRunnable)
    }

    private fun updateAge() {
        val now = System.currentTimeMillis()
        val diffMs = now - birthTimeMillis
        
        // Calculate age in years (365.2425 days per year on average)
        val msPerYear = 365.2425 * 24 * 60 * 60 * 1000.0
        val age = diffMs.toDouble() / msPerYear
        
        // Format to 9 decimal places
        ageTextView.text = String.format(Locale.US, "%.9f", age)

        // Calculate hours lived
        val hoursLived = diffMs / (1000 * 60 * 60)
        hoursLivedTextView.text = String.format(Locale.getDefault(), "%,d", hoursLived)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
    }
}