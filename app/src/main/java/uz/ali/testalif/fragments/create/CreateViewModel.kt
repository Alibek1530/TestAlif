package uz.ali.testalif.fragments.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsm.roomdaggerhilt.db.RoomRepository
import com.tsm.roomdaggerhilt.db.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateViewModel @Inject constructor(private val repository:RoomRepository):ViewModel(){

    fun insertRecord(taskEntity: TaskEntity){
        repository.insertRecord(taskEntity)
    }

    fun getTask(id:Long): LiveData<TaskEntity> {
        return repository.getTask(id)
    }

    fun deleteTask(id:Long){
        repository.deleteTask(id)
    }

}