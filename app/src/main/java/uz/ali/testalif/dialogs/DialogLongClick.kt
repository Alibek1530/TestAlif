package uz.ali.testalif.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import uz.ali.testalif.R
import uz.ali.testalif.databinding.DialogLongclickBinding
import uz.ali.testalif.fragments.main.FragmentMain

class DialogLongClick(var mainFragment: FragmentMain) : AlertDialog(mainFragment.requireContext()) {

    private val binding = DialogLongclickBinding.inflate(LayoutInflater.from(mainFragment.context))


    init {
        window!!.getAttributes().windowAnimations = R.style.DialogThemeLongClick
        window!!.getAttributes().gravity = Gravity.CENTER
        val displayMetrics = DisplayMetrics()
        window!!.windowManager.defaultDisplay.getRealMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val wid = width * 1.0 / 20.0

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, wid.toInt(), 0, wid.toInt(), 0)
        window!!.setBackgroundDrawable(inset)
        setCancelable(true)

        binding.cancelDialog.setOnClickListener {
            dismiss()
        }
        binding.tvDeleteClick.setOnClickListener {
            mainFragment.setDeleteDialog()
            dismiss()
        }
        binding.tvEditClick.setOnClickListener {
            mainFragment.setEditeDialog()
            dismiss()
        }

        setView(binding.root)
    }

}