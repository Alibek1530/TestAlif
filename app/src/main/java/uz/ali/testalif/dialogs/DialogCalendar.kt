package uz.ali.testalif.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.ali.testalif.R
import uz.ali.testalif.databinding.DialogCalendarBinding
import uz.ali.testalif.fragments.create.FragmentCreate
import java.text.SimpleDateFormat
import java.util.*

class DialogCalendar(val createFragment: FragmentCreate) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogX.setOnClickListener {
            dismiss()
        }
        var calendar = Calendar.getInstance()
        binding.Calendar.minDate = calendar.timeInMillis

        binding.Calendar.setOnDateChangeListener { calendarView, i, i2, i3 ->
           val calendarDate = "${i2 + 1} ${i3}, ${i}"


            val spf = SimpleDateFormat("MM dd, yyyy", Locale("RU"))
            val newDate: Date = spf.parse(calendarDate)

            val spf1 = SimpleDateFormat("dd MMMM yyyy", Locale("RU"))
            val displayShow = spf1.format(newDate)
            dismiss()
            val dateMilli = getDateToMilli(calendarDate)
            createFragment.setDate(dateMilli, displayShow)
        }
    }

    fun getDateToMilli(oldTime: String): Long {
        val formatter = SimpleDateFormat("MM dd, yyyy")
        formatter.isLenient = false
        val oldDate = formatter.parse(oldTime)
        return oldDate.time
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}