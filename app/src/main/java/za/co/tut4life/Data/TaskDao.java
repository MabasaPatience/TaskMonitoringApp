package za.co.tut4life.Data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import za.co.tut4life.model.TaskModel;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(TaskModel taskModel);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    LiveData<List<TaskModel>> getTask();

    @Query("SELECT * FROM task_table WHERE task_table.task_id== :id")
    LiveData<TaskModel> get(long id);
    @Update
    void update(TaskModel task);

    @Delete
    void delete(TaskModel task);
}
