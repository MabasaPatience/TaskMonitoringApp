package za.co.tut4life.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import za.co.tut4life.Util.TaskRoomDatabase;
import za.co.tut4life.model.TaskModel;

public class DoisterRepository {

    private  final TaskDao taskDao;
    private  final LiveData<List<TaskModel>> allTask;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database=TaskRoomDatabase.getDatabase(application);
       this.taskDao =database.taskDao() ;
       this.allTask = taskDao.getTask();
    }

    public LiveData<List<TaskModel>> getAllTask(){
        return allTask;
    }
    public void insert(TaskModel task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->taskDao.insertTask(task));
    }
    public LiveData<TaskModel> get(long id){
        return taskDao.get(id);
    }
    public  void update (TaskModel task ){
        TaskRoomDatabase.databaseWriterExecutor.execute(() ->taskDao.update(task));
    }
    public void delete(TaskModel task){
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.delete(task));
    }
}
