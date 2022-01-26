package uz.ali.testalif.db.pref

import android.content.Context
import android.net.Uri
import uz.ali.testalif.App

class SharePref {
    private val pref =
        App.instance.getSharedPreferences("alif", Context.MODE_PRIVATE)


    var countTime: Long
        set(value) {
            pref.edit().putLong("countTime", value).apply()
        }
        get() {
            return pref.getLong("countTime",1)
        }

    var imageUri: String
        set(value) {
            pref.edit().putString("imageUri", value).apply()
        }
        get() {
            return pref.getString("imageUri","notImage").toString()
        }


    fun addTimeNotif(index:Long,timeAlarm:Long) {
        pref.edit().putLong("addTimeNotif${index}", timeAlarm).apply()
    }
    fun getTimeNotif(index:Long): Long {
        return pref.getLong("addTimeNotif${index}",0)
    }

    fun addBeforeNotif(index:Long,before:String) {
        pref.edit().putString("addBeforeNotif${index}", before).apply()
    }
    fun getBeforeNotif(index:Long): String {
        return pref.getString("addBeforeNotif${index}","0").toString()
    }

    fun addTitleNotif(index:Long,title:String) {
        pref.edit().putString("addTitleNotif${index}", title).apply()
    }
    fun getTitleNotif(index:Long): String {
        return pref.getString("addTitleNotif${index}","0").toString()
    }

    fun addStatuseNotif(index:Long,status:String) {
        pref.edit().putString("addStatuseNotif${index}", status).apply()
    }
    fun getStatuseNotif(index:Long): String {
        return pref.getString("addStatuseNotif${index}","0").toString()
    }
}