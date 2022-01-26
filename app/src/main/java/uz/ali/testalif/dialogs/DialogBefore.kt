package uz.ali.testalif.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.ali.testalif.R
import uz.ali.testalif.adapters.BeforeAdapter
import uz.ali.testalif.adapters.MainAdapter
import uz.ali.testalif.databinding.DialogBeforeBinding
import uz.ali.testalif.databinding.DialogCalendarBinding
import uz.ali.testalif.fragments.create.FragmentCreate
import java.text.SimpleDateFormat
import java.util.*

class DialogBefore(val createFragment: FragmentCreate) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogBeforeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBeforeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogX.setOnClickListener {
            dismiss()
        }
        binding.RecyclerBefore.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.RecyclerBefore.adapter = BeforeAdapter(getListBefore(),this)
    }
    fun getListBefore():List<Long>{
        var list= arrayListOf<Long>()
        list.add(0)
        list.add(5)
        list.add(10)
        list.add(15)
        list.add(30)
        list.add(45)
        return list
    }

    fun setData(before:Long){
        createFragment.setBefore(before)
        dismiss()
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}