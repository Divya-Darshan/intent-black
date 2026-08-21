package com.darshnidev.black

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.SystemClock
import android.widget.RemoteViews
import java.util.Calendar

class LifeWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val birth = Calendar.getInstance().apply { set(2006, Calendar.FEBRUARY, 20, 0, 0, 0) }
        val end = Calendar.getInstance().apply { set(2086, Calendar.FEBRUARY, 20, 0, 0, 0) }
        val now = Calendar.getInstance()

        val totalMs = end.timeInMillis - birth.timeInMillis
        val livedMs = now.timeInMillis - birth.timeInMillis
        val remainingMs = end.timeInMillis - now.timeInMillis

        val percentLived = ((livedMs.toDouble() / totalMs.toDouble()) * 100).toInt().coerceIn(0, 100)
        val chronometerBase = SystemClock.elapsedRealtime() + remainingMs

        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_life_layout)
            
            // System-level Chronometer for 0% battery live countdown
            views.setChronometerCountDown(R.id.live_countdown, true)
            views.setChronometer(R.id.live_countdown, chronometerBase, null, true)
            
            views.setTextViewText(R.id.percent_lived, "$percentLived% LIVED")

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}