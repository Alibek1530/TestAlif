package uz.ali.testalif

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.ali.testalif.databinding.ActivityMainBinding
import uz.ali.testalif.db.pref.SharePref
import uz.ali.testalif.notification.AlarmReceiver
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instanceee = this

        setNavigation()

        ignoreBatteryOptimization()
        setNotification()

        setAlarm()

        onBackPassedfun()
    }

    companion object {
        lateinit var instanceee: MainActivity private set
    }

    fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.bottomNav.setupWithNavController(navController)
    }

    fun setNotification() {
        val name = "foxandroidReminderChannel"
        val description = "Alarm Manager"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("alifandroid", name, importance)
        channel.description = description
        val notificationManager = getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(channel)
    }

    private fun ignoreBatteryOptimization() {
        val intent = Intent()
        val packN = packageName
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packN)) {
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packN")
            startActivity(intent)
        }
    }

    fun setAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            1000 * 2 * 1,
            pendingIntent
        )
    }

    fun setNavigationHidden(b: Boolean) {
        if (b) {
            binding.bottomNav.animate().translationY(0f)
        } else {
            binding.bottomNav.animate()
                .translationY(binding.bottomNav.height.toFloat())
        }
    }

    private fun onBackPassedfun() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed1()
            }
        }
        onBackPressedDispatcher.addCallback(callback)
    }

    var counter = 0
    fun onBackPressed1() {
        counter++
        if (counter > 1) {
            finishAffinity()
        } else {
            Toast.makeText(this, "Чтобы выйти из программы, нажмите ещё раз.", Toast.LENGTH_SHORT)
                .show()
        }
        val DELAY_TIME = 1000L
        Thread(Runnable {
            try {
                Thread.sleep(DELAY_TIME)
                counter = 0
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()
    }
}
