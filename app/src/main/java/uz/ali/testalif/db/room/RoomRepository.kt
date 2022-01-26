package com.tsm.roomdaggerhilt.db

import androidx.lifecycle.LiveData
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao){

    fun getTaskAll():LiveData<List<TaskEntity>>{
        return appDao.getTaskAll()
    }
    fun getTaskAllDesc():LiveData<List<TaskEntity>>{
        return appDao.getTaskAllDesc()
    }

    fun getTaskDone(status:String):LiveData<List<TaskEntity>>{
        return appDao.getTaskDone(status)
    }


    fun getTasksToday(dateToday:String):LiveData<List<TaskEntity>>{
        return appDao.getSortToday(dateToday)
    }

    fun insertRecord(taskEntity: TaskEntity){
        appDao.insertRecord(taskEntity)
    }
    fun updateStatus(id:Long,status:String){
        appDao.updateStatus(id,status)
    }

    fun deleteTask(id:Long){
        appDao.deleteTask(id)
    }

    fun getTask(id:Long):LiveData<TaskEntity>{
        return appDao.getTask(id)
    }

}

