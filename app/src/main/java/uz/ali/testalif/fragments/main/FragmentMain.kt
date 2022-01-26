package uz.ali.testalif.fragments.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.tsm.roomdaggerhilt.db.TaskEntity
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import uz.ali.testalif.R
import uz.ali.testalif.adapters.MainAdapter
import uz.ali.testalif.databinding.FragmentMainBinding
import uz.ali.testalif.db.pref.SharePref
import uz.ali.testalif.dialogs.DialogLongClick
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FragmentMain : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var pref: SharePref
    var dateToday = ""
    var posCheck = -1
    var countTask = 0
    var idTask: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharePref()
        dateToday = getFormatTimeWithTZ("yyyy-MM-dd", Date(System.currentTimeMillis()))

        setDateData()
        setImage()

        setRecycler()

        binding.flatBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_doneFragment)
        }
        binding.imageUser.setOnClickListener {
            setOpenImage()
        }

    }

    fun setRecycler() {
        binding.Recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setRecData()
    }

    fun setRecData() {
//        viewModel.getTasksTodayObserver(dateToday).observe(requireActivity(), {
//            binding.Recycler.adapter = MainAdapter(getListTasks(it), this, posCheck)
//        })

        viewModel.getRecordsObserver().observe(requireActivity(), {
            binding.Recycler.adapter = MainAdapter(getListTasks(it), this, posCheck)

            binding.taskCount.setText("${posCheck + 1}/${countTask} Задача сегодня")
        })
    }

    fun setDateData() {
        binding.tvDate.setText(
            getFormatTimeWithTZ(
                "dd MMMM yyyy",
                Date(System.currentTimeMillis())
            )
        )
        binding.tvDay.setText(getDateDay())
    }

    fun getDateDay(): String {
        val cal = Calendar.getInstance()
        val kun = cal.get(Calendar.DAY_OF_WEEK)
        val days = arrayOf(
            "Воскресенье",
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота"
        )
        return days[kun - 1]
    }

    fun getListTasks(l: List<TaskEntity>): List<TaskEntity> {
        var list = arrayListOf<TaskEntity>()
        countTask = l.size
        posCheck = -1
        l.forEach {
            if (it.status == "true") {
                list.add(it)
                posCheck++
            }
        }
        l.forEach {
            if (it.status == "false") {
                list.add(it)
            }
        }
        return list
    }

    fun setItemChekClick(id: Long, chek: Boolean) {
        idTask = id
        viewModel.updateStatus(id, chek.toString())
        pref.addStatuseNotif(id, chek.toString())
        setRecData()
    }

    fun setOnLongClick(id: Long) {
        idTask = id
        val dialog = DialogLongClick(this)
        dialog.show()
    }

    fun setDeleteDialog() {
        viewModel.deleteTask(idTask)
        deletePrefTask()
        setRecData()
    }

    fun setEditeDialog() {
        deletePrefTask()
        val bundle = Bundle()
        bundle.putLong("id", idTask)
        findNavController().navigate(R.id.action_mainFragment_to_editeFragment, bundle)
    }

    fun deletePrefTask() {
        pref.addTimeNotif(idTask, 0)
        pref.addTitleNotif(idTask, "0")
        pref.addStatuseNotif(idTask, "0")
        pref.addBeforeNotif(idTask, "0")
    }

    fun getFormatTimeWithTZ(format: String, currentTime: Date): String {
        val timeZoneDate = SimpleDateFormat(format, Locale.getDefault())
        return timeZoneDate.format(currentTime)
    }

    //image

    private fun setOpenImage() {
        if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSON_CODE)
        } else {
            pickImageFromGalery()
        }
    }

    private fun pickImageFromGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    val IMAGE_PICK_CODE = 1000
    val PERMISSON_CODE = 1001

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSON_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGalery()
                } else {
                    Toast.makeText(requireContext(), "Разрешить приложению доступ к фото", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.data?.let {
                pref.imageUri = it.toString()
                setImage()
            }
        }
    }

    fun setImage() {
        Picasso.get().load(pref.imageUri)
            .transform(CropCircleTransformation())
            .into(binding.imageUser)
    }
}