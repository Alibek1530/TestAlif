package uz.ali.testalif.fragments.progress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsm.roomdaggerhilt.db.TaskEntity
import dagger.hilt.android.AndroidEntryPoint
import uz.ali.testalif.Models.TaskModel
import uz.ali.testalif.adapters.ItemsAdapter
import uz.ali.testalif.databinding.FragmentAllBinding
import uz.ali.testalif.databinding.FragmentInProgressBinding
import uz.ali.testalif.fragments.all.AllViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FragmentInProgress : Fragment() {
    lateinit var binding: FragmentInProgressBinding
    lateinit var viewModel: ProgressViewModel
    var dateToday = ""
    var currentTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInProgressBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ProgressViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentTime = System.currentTimeMillis()
        dateToday = getFormatTimeWithTZ("yyyy-MM-dd", Date(currentTime))

        setRecycler()
        setRecData()
    }

    fun setRecycler() {
        binding.Recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun setRecData() {
        viewModel.getTasksTodayObserver(dateToday).observe(requireActivity(), {
            binding.Recycler.adapter = ItemsAdapter(getSortProgress(it))

            binding.taskCount.setText("${it.size} Задача")
        })
    }

    fun getSortProgress(l: List<TaskEntity>): List<TaskEntity> {
        var list = arrayListOf<TaskEntity>()
        var pos = l.size - 1

        for (i in 0..l.size - 1) {
            val status = l[i].status
            if (status == "false") {
                pos = i
            }
        }

        for (i in 0..l.size - 2) {
            val time = l[i].timeMille + l[i].dateMilli
            val time1 = l[i + 1].timeMille + l[i + 1].dateMilli
            val status = l[i].status

            if (time < currentTime && time1 > currentTime && status == "false") {
                pos = i
            }
        }
        if (l.size - 1 >= 0) {
            list.add(l[pos])
        }

        return list
    }

    fun getFormatTimeWithTZ(format: String, currentTime: Date): String {
        val timeZoneDate = SimpleDateFormat(format, Locale.getDefault())
        return timeZoneDate.format(currentTime)
    }

}