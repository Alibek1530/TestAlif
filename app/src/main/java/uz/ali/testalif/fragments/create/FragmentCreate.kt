package uz.ali.testalif.fragments.create

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tsm.roomdaggerhilt.db.TaskEntity
import dagger.hilt.android.AndroidEntryPoint
import uz.ali.testalif.MainActivity
import uz.ali.testalif.R
import uz.ali.testalif.databinding.FragmentCreateBinding
import uz.ali.testalif.db.pref.SharePref
import uz.ali.testalif.dialogs.DialogBefore
import uz.ali.testalif.dialogs.DialogCalendar
import uz.ali.testalif.dialogs.DialogTime
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FragmentCreate : Fragment() {
    lateinit var binding: FragmentCreateBinding
    lateinit var viewModel: CreateViewModel
    lateinit var pref: SharePref
    var timeMilli: Long = 0
    var beforeMilli: Long = 0
    var before: String = ""
    var dateMilli: Long = 0
    var status = "false"
    var idTask: Long? = null
    var isOne = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        isOne = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharePref()

        idTask = arguments?.getLong("id")
        if (idTask != null) {
            setEditeTask()
        }


        binding.linDate.setOnClickListener {
            val dialog = DialogCalendar(this)
            dialog.show(parentFragmentManager, "@@@")
        }
        binding.linTime.setOnClickListener {
            val dialog = DialogTime(this)
            dialog.show(parentFragmentManager, "@@@")
        }
        binding.linAlarm.setOnClickListener {
            val dialog = DialogBefore(this)
            dialog.show(parentFragmentManager, "@@@")
        }

        binding.btnSave.setOnClickListener {
            setOnSaveBtnIs()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun setOnSaveBtnIs() {
        if (binding.EditeTitle.text.toString() != "" && binding.TextDate.text.toString() != ""
            && binding.TextTime.text.toString() != "" && binding.TextAlarm.text.toString() != ""
        ) {
            MainActivity.instanceee.setNavigationHidden(false)
            setSave()
            showSnackBar("Задача добавлена")
        } else {
            MainActivity.instanceee.setNavigationHidden(false)
            vibratePhone()
            animationSaveBtn()
            showSnackBar("Заполните поля")
        }
    }

    fun setSave() {
        val milliseconds = dateMilli + timeMilli
        val date = Date(milliseconds)
        val mobileDate = getFormatTimeWithTZ(date)
        val mobileDateTime = getFormatTimeWithTZMin(date)

        val userEntity = TaskEntity(
            name = binding.EditeTitle.text.toString(),
            date = mobileDate,
            time = mobileDateTime,
            alarmTime = before,
            status = status,
            dateMilli = dateMilli,
            timeMille = timeMilli
        )
        val posId = pref.countTime

        if (idTask != null) {
            Log.d("qanekorik", "setSave1:${idTask} ")

            viewModel.deleteTask(idTask!!)
        }
        viewModel.insertRecord(userEntity)
        val alarm = milliseconds - beforeMilli
        pref.addTimeNotif(posId, alarm)
        pref.addBeforeNotif(posId, before)
        pref.addTitleNotif(posId, binding.EditeTitle.text.toString())
        pref.addStatuseNotif(posId, status)

        pref.countTime = posId + 1
        idTask = null
        Log.d("qanekorik", "setSave2:${idTask} ")
        findNavController().navigateUp()
    }


    fun getFormatTimeWithTZ(currentTime: Date): String {
        val timeZoneDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return timeZoneDate.format(currentTime)
    }

    fun getFormatTimeWithTZMin(currentTime: Date): String {
        val timeZoneDate = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeZoneDate.format(currentTime)
    }

    fun setDate(dateMilli: Long, date: String) {
        this.dateMilli = dateMilli
        Log.d("date", "setDate: ${dateMilli} : ${date}")
        binding.TextDate.setText(date)
    }

    fun setTime(timeMilli: Long, time: String) {
        this.timeMilli = timeMilli
        Log.d("date", "setDate: ${timeMilli} : ${time}")
        binding.TextTime.setText(time)
    }

    fun setBefore(beforeMilli: Long) {
        before = beforeMilli.toString()
        this.beforeMilli = beforeMilli * 60 * 1000
        binding.TextAlarm.setText("За ${beforeMilli} минут до")
    }

    fun setEditeTask() {
        viewModel.getTask(idTask!!).observe(requireActivity(), {
            if (isOne) {
                binding.EditeTitle.setText(it.name)

                this.status = it.status

                binding.TextDate.setText(setDateFormat(it.date))
                this.dateMilli = it.dateMilli

                binding.TextTime.setText(it.time)
                this.timeMilli = it.timeMille

                binding.TextAlarm.setText("За ${it.alarmTime} минут до")
                this.beforeMilli = it.alarmTime.toLong() * 60 * 1000
                this.before = it.alarmTime
            }
            isOne = false
        })
        binding.tvBtn.setText("Редактировать")
        binding.tvTitle.setText("Редактировать")
    }

    fun setDateFormat(calendarDate: String): String {
        val spf = SimpleDateFormat("yyyy-MM-dd", Locale("RU"))
        val newDate: Date = spf.parse(calendarDate)
        val spf1 = SimpleDateFormat("dd MMMM yyyy", Locale("RU"))
        return spf1.format(newDate)
    }


    private fun showSnackBar(text:String) {
        val snack = Snackbar.make(binding.btnSave, text, Snackbar.LENGTH_LONG)
        snack.setActionTextColor(resources.getColor(R.color.black))
        snack.setTextColor(resources.getColor(R.color.white))
        snack.setBackgroundTint(resources.getColor(R.color.color_fio))
        snack.setAction("Axa") {
        }
        snack.show()

        snack.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                MainActivity.instanceee.setNavigationHidden(true)
            }
        })
    }

    fun vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    var isStop = 0
    private fun animationSaveBtn() {
        isStop++
        val animationTopLeft: Animation = TranslateAnimation(-50f, 0f, 0f, 0f)
        animationTopLeft.duration = 100
        animationTopLeft.fillAfter = true
        binding.btnSave.startAnimation(animationTopLeft)
        animationTopLeft.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                //RightAnimation
                val animationTopRight: Animation = TranslateAnimation(50f, 0f, 0f, 0f)
                animationTopRight.duration = 100
                animationTopRight.fillAfter = true
                binding.btnSave.startAnimation(animationTopRight)
                animationTopRight.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        if (isStop < 4) {
                            animationSaveBtn()
                        }else{
                            isStop=0
                        }
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                })
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
    }

    override fun onStop() {
        super.onStop()
        if (idTask != null) {
            setSave()
        }
    }

}