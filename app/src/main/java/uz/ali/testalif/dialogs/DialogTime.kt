package uz.ali.testalif.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.ali.testalif.R
import uz.ali.testalif.databinding.DialogTimeBinding
import uz.ali.testalif.fragments.create.FragmentCreate
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DialogTime(val createFragment: FragmentCreate) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogTimeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogX.setOnClickListener {
            dismiss()
        }
        binding.Time.setIs24HourView(true)


        binding.dialogX.setOnClickListener {

            var b= binding.Time.minute
            var c= binding.Time.hour
            Log.d("ali", "onViewCreated:  ${b} : ${c}")
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val min= binding.Time.minute
            val soat= binding.Time.hour
            Log.d("ali", "onViewCreated:  ${soat} : ${min}")
            val timeMilli=getTimeToMilli(soat,min)
            createFragment.setTime(timeMilli,"${setMinGet0(soat)}:${setMinGet0(min)}")
            dismiss()
        }
    }
    fun setMinGet0(time:Int):String{
        if (time-10>=0){
            return time.toString()
        }else{
            return "0${time}"
        }
    }

    fun getTimeToMilli(soat: Int,min:Int): Long {
        val s=soat*3600000
        val m=min*60000
        val milliseconds=s+m
        return milliseconds.toLong()
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}