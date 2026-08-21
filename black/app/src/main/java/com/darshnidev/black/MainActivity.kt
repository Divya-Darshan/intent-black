package com.darshnidev.black

import android.app.Activity
import android.content.Intent
import android.provider.CallLog
import android.os.Bundle

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(Intent.ACTION_VIEW, CallLog.Calls.CONTENT_URI))
        finish()
    }
}