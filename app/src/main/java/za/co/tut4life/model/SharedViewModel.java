package za.co.tut4life.model;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<TaskModel> selectedItem=new MutableLiveData<>();

    public void selectItem(TaskModel task){

        selectedItem.setValue(task);
    }
    public LiveData<TaskModel> getSelectedItem(){
        return selectedItem;
    }

}
