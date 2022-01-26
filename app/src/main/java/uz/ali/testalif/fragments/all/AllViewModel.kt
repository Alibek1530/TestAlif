package uz.ali.testalif.fragments.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsm.roomdaggerhilt.db.RoomRepository
import com.tsm.roomdaggerhilt.db.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllViewModel @Inject constructor(private val repository:RoomRepository):ViewModel(){

    fun getAllTaskObserver(): LiveData<List<TaskEntity>> {
        return repository.getTaskAllDesc()
    }
}