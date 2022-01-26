package uz.ali.testalif.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsm.roomdaggerhilt.db.RoomRepository
import com.tsm.roomdaggerhilt.db.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository:RoomRepository):ViewModel(){


    fun getRecordsObserver(): LiveData<List<TaskEntity>> {
        return repository.getTaskAll()
    }
    fun getTasksTodayObserver(dateToday:String): LiveData<List<TaskEntity>> {
        return repository.getTasksToday(dateToday)
    }


    fun insertRecord(taskEntity: TaskEntity){
        repository.insertRecord(taskEntity)
    }
    fun updateStatus(id:Long, status:String){
        repository.updateStatus(id, status)
    }
    fun deleteTask(id:Long){
        repository.deleteTask(id)
    }



}