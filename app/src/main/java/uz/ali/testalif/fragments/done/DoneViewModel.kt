package uz.ali.testalif.fragments.done

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsm.roomdaggerhilt.db.RoomRepository
import com.tsm.roomdaggerhilt.db.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DoneViewModel @Inject constructor(private val repository:RoomRepository):ViewModel(){

    fun getTaskDoneObserver(status:String): LiveData<List<TaskEntity>> {
        return repository.getTaskDone(status)
    }
}