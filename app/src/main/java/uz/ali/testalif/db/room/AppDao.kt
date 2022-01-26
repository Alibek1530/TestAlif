package com.tsm.roomdaggerhilt.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDao {
    @Query("SELECT * FROM task ORDER BY time ASC")
    fun getTaskAll(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM task ORDER BY time DESC")
    fun getTaskAllDesc(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE status=:status ORDER BY time DESC")
    fun getTaskDone(status:String): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE date=:dateToday ORDER BY time ASC")
    fun getSortToday(dateToday:String): LiveData<List<TaskEntity>>

    @Query("UPDATE task SET status=:statuss WHERE id=:id")
    fun updateStatus(id:Long,statuss:String)

    @Query("DELETE FROM task WHERE id = :id")
    fun deleteTask(id:Long)

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTask(id:Long): LiveData<TaskEntity>

    @Insert
    fun insertRecord(taskEntity: TaskEntity)
}