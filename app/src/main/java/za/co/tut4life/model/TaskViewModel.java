package za.co.tut4life.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import za.co.tut4life.Data.DoisterRepository;

public class TaskViewModel extends AndroidViewModel {

public static DoisterRepository repository;
public final LiveData<List<TaskModel>> allTask;



    public TaskViewModel(Application application) {
        super(application);
        repository=new DoisterRepository(application);
        allTask=repository.getAllTask();
    }
    public LiveData<List<TaskModel>> getAllTask(){return allTask;}
    public static void insert(TaskModel task){repository.insert(task);}
    public LiveData<TaskModel> get(long id){return repository.get(id);}
    public static void update(TaskModel task){repository.update(task);}
    public static void delete(TaskModel task){ repository.delete(task);}
}
