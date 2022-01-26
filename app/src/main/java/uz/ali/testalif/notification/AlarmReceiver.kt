package uz.ali.testalif.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import uz.ali.testalif.MainActivity
import uz.ali.testalif.db.pref.SharePref
import java.util.*


import android.content.ContentResolver
import android.media.AudioManager
import android.net.Uri
import android.media.AudioAttributes

import android.app.NotificationManager

import android.app.NotificationChannel
import android.graphics.Color

import android.os.Build
import android.provider.Settings.Global.getString
import androidx.room.Room
import com.tsm.roomdaggerhilt.db.AppDatabase
import uz.ali.testalif.App
import uz.ali.testalif.R


class  AlarmReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val pref = SharePref()
       val time=System.currentTimeMillis()
        val a=time-1*1000*60
        val b=time+1*1000*60

        for (i in 0..pref.countTime-1){
            val alarmTime=pref.getTimeNotif(i)
            val status=pref.getStatuseNotif(i)

            if ((alarmTime>a) && (alarmTime<b) && (alarmTime!=0L) && status=="false"){
                pref.addTimeNotif(i,0L)

                val title=pref.getTitleNotif(i)
                val beforeMin=pref.getBeforeNotif(i)

                setNotiff(context,intent,title,beforeMin)
            }
        }
    }

    fun setNotiff(context: Context?, intent: Intent?,title:String,beforeMin:String){

        val i = Intent(context, MainActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val CHANNEL_ID = "1234"

        val soundUri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context?.packageName + "/" + R.raw.sound
        )
        val mNotificationManager =
            NotificationManagerCompat.from(context!!)

        val mChannel: NotificationChannel
        mChannel = NotificationChannel(
            CHANNEL_ID,
            "alifandroid",
            NotificationManager.IMPORTANCE_HIGH
        )
        mChannel.lightColor = Color.GRAY
        mChannel.enableLights(true)
        mChannel.description = "За ${beforeMin} минут до"
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        mChannel.setSound(soundUri, audioAttributes)
        mNotificationManager.createNotificationChannel(mChannel)

        val status = NotificationCompat.Builder(
            context!!,
            CHANNEL_ID
        )
        status.setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentTitle(title)
            .setContentText("За ${beforeMin} минут до")
            .setVibrate(longArrayOf(100, 200, 500, 400, 700, 400, 300))
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.sound))
            .setContentIntent(pendingIntent)

        mNotificationManager!!.notify(456, status.build())
    }
}